package com.in28minutes.springboot.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.in28minutes.springboot.dtos.ReservationDTO;
import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.model.GuestLecturer;
import com.in28minutes.springboot.model.Student;

@Component
public class StudentService_Spring_JDBC implements IStudentService
{
	// FIELDS
	Logger logger = LoggerFactory.getLogger(StudentService_Spring_JDBC.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	ReactiveProducerService reactiveProducerService = null;
	
	
	// CONSTRUCTORS
	public StudentService_Spring_JDBC()
	{}
	
	
	
	public StudentService_Spring_JDBC(ReactiveProducerService reactiveProducerService)
	{
		super();
		this.reactiveProducerService = reactiveProducerService;
	}
	
	
	
	
	// MAIN METHODS
	@Override
	public Student retrieveStudent(String studentId)
	{
		logger.info("retrieveStudent() called on: " + studentId);
		
		// check if studentID valid number
		// not really necessary but if we're not getting passed a valid number, we can
		// handle (log) the error
		int id = getIntegerID(studentId);
		
		// first get the students (without initializing the courses)
		Student student = retrieveStudentByIDViaDB(id);
		
		// secondly, we add the course data (the course steps are missing though)
		List<Course> studentCourses = retrieveCoursesByStudentIDViaDB(id);
		
		// finally, we add the course step data to the courses
		initCourseStepsViaDB(studentCourses);
		
		// make sure everything is set properly
		student.setCourses(studentCourses);
		
		logger.info("retrieveStudent() result: " + student);
		
		return student;
	}
	
	
	
	@Override
	public List<Student> retrieveAllStudents()
	{
		logger.info("retrieveAllStudents() called");
		List<Student> students = retrieveAllStudentsViaDB();
		logger.info("retrieveAllStudents() result: " + students);
		return students;
	}
	
	
	@Override
	public List<Course> retrieveCourses(String studentId)
	{
		logger.info("retrieveCourses() called on: " + studentId);
		int id = getIntegerID(studentId);
		List<Course> courses = retrieveCoursesByStudentIDViaDB(id);
		initCourseStepsViaDB(courses);
		logger.info("retrieveCourses() result: " + courses);
		return courses;
	}
	
	@Override
	public Course retrieveCourse(String studentId, String courseId)
	{
		logger.info("retrieveCourses() called on student " + studentId + " and course " + courseId);
		int sID = getIntegerID(studentId);
		int cID = getIntegerID(courseId);
		Course course = retrieveCourseByIDsViaDB(sID, cID);
		logger.info("retrieveCourse() result: " + course);
		return course;
	}
	
	@Override
	public Course addCourseToCurriculum(String studentId, Course course)
	{
		// remember that name is UNIQUE in the SQL table 'courses'
		logger.info("addCourseToCurriculum() called on student " + studentId + " and course " + course.getName());
		
		int sID = getIntegerID(studentId);
		
		// returns -1 for new courses, courseID for pre-existing ones
		int courseID = getExistingCourseID(course);
		logger.info("addCourseToCurriculum() courseID: " + courseID);
		
		if (courseID < 1)
		{
			// insert new course
			insertNewCourse(course);
			
			// get auto-generated ID of new course
			courseID = setNewCourseID(course);
			
			// insert coursesteps into new course
			insertCourseSteps(courseID, course);
		}
		else
		{
			// set proper ID for existing courses for the response body
			// TODO send a no content result in this case
			course.setId(String.valueOf(courseID));
		}
		
		// register student for course
		try
		{
			jdbcTemplate.update("INSERT INTO registered_for (student_id, course_id) VALUES (" + sID + ", " + courseID + ");");
		}
		catch (DataIntegrityViolationException e)
		{
			// if this happens, the student was already registered and we can fail without a
			// full stack trace
			logger.warn("addCourseToCurriculum(): student " + studentId + " has already been registered for course " + course.getName());
		}
		
		logger.info("addCourseToCurriculum() result: " + course);
		
		return course;
	}
	
	
	@Override
	public List<GuestLecturer> retrieveAllGuestLecturers()
	{
		logger.info("retrieveAllGuestLecturers() called");
		List<GuestLecturer> guests = retrieveAllGuestsViaDB();
		logger.info("retrieveAllGuestLecturers() result: " + guests);
		return guests;
	}
	
	
	
	@Override
	public Course registerGuestLecturerForCourse(String guestId, Course newCourse)
	{
		// remember that name is UNIQUE in the SQL table 'courses'
		logger.info("registerGuestLecturerForCourse() called on student " + guestId + " and course " + newCourse.getName());
		
		int gID = getIntegerID(guestId);
		
		// returns -1 for new courses, courseID for pre-existing ones
		int courseID = getExistingCourseID(newCourse);
		logger.info("registerGuestLecturerForCourse() courseID: " + courseID);
		
		if (courseID < 1)
		{
			// insert new course
			insertNewCourse(newCourse);
			
			// get auto-generated ID of new course
			courseID = setNewCourseID(newCourse);
			
			// insert coursesteps into new course
			insertCourseSteps(courseID, newCourse);
		}
		else
		{
			// set proper ID for existing courses for the response body
			// TODO send a no content result in this case
			newCourse.setId(String.valueOf(courseID));
		}
		
		// register guest lecturer for course
		try
		{
			jdbcTemplate.update("INSERT INTO holds_guest_lecture (guest_id, course_id) VALUES (" + gID + ", " + courseID + ");");
			// insertion successful, now comes the fun part: booking the guest lecturer's
			// board & lodging in some local hotel for the duration of the stay
			// the important part: a "remote" rest service is responsible for that
			// it needs to be notified (if we're communicating with the reservation-service,
			// that is)
			// is this kosher?
			if (Arrays.asList(env.getActiveProfiles()).contains("kafka") || Arrays.asList(env.getActiveProfiles()).contains("rabbitmq"))
			{
				bookLodgingsForGuestLecturer(gID);
			}
			
		}
		catch (DataIntegrityViolationException e)
		{
			// if this happens, the student was already registered and we can fail without a
			// full stack trace
			logger.warn("registerGuestLecturerForCourse(): guest lecturer " + guestId + " has already been registered for course " + newCourse.getName());
		}
		
		logger.info("registerGuestLecturerForCourse() result: " + newCourse);
		
		return newCourse;
		
	}
	
	
	
	
	
	
	
	
	
	// HELPER METHODS
	// general
	private List<Course> retrieveCoursesByStudentIDViaDB(int id)
	{
		List<Course> studentCourses = jdbcTemplate.query(
				"SELECT * FROM registered_for rf INNER JOIN courses c ON rf.course_id = c.id WHERE rf.student_id=" + id,
				new BeanPropertyRowMapper<Course>(Course.class));
		logger.debug("retrieveCoursesByStudentIDViaDB() results: " + studentCourses);
		return studentCourses;
	}
	
	private List<Course> retrieveCoursesByGuestIDViaDB(int id)
	{
		List<Course> guestLecturerCourses = jdbcTemplate.query(
				"SELECT * FROM holds_guest_lecture hgl INNER JOIN courses c ON hgl.course_id = c.id WHERE hgl.guest_id=" + id,
				new BeanPropertyRowMapper<Course>(Course.class));
		logger.debug("retrieveCoursesByGuestIDViaDB() results: " + guestLecturerCourses);
		return guestLecturerCourses;
	}
	
	private void initCourseStepsViaDB(List<Course> studentCourses)
	{
		for (Course course : studentCourses)
		{
			initCourseStepsViaDB(course);
		}
	}
	
	private void initCourseStepsViaDB(Course course)
	{
		List<String> courseSteps = jdbcTemplate.queryForList(
				"SELECT cs.description FROM courses c INNER JOIN coursesteps cs ON c.id = cs.course_id WHERE c.id=" + course.getId(), String.class);
		logger.debug("initCourseStepsViaDB() results for " + course.getName() + ": " + courseSteps);
		course.setSteps(courseSteps);
	}
	
	private int getIntegerID(String identifier) throws NumberFormatException
	{
		int id;
		try
		{
			id = Integer.valueOf(identifier);
		}
		catch (NumberFormatException e)
		{
			logger.warn("getIntegerID() was not passed a valid integer; instead, it was passed this: " + identifier);
			return -1; // should cause an empty result set down the line;
		}
		return id;
	}
	
	
	// helpers for: retrieveStudent()
	private Student retrieveStudentByIDViaDB(int id)
	{
		List<Student> resultWithoutCourses = jdbcTemplate.query("SELECT * FROM students WHERE students.id=" + id,
				new BeanPropertyRowMapper<Student>(Student.class));
		if ((resultWithoutCourses == null) || (resultWithoutCourses.size() != 1)) return null;
		
		logger.debug("retrieveStudentByIDViaDB() results: " + resultWithoutCourses);
		
		Student student = resultWithoutCourses.get(0);
		return student;
	}
	
	
	// helpers for: retrieveAllStudents()
	private List<Student> retrieveAllStudentsViaDB()
	{
		// allStudents are intially without courses and courses later in turn without
		// coursesteps
		List<Student> allStudents = jdbcTemplate.query("SELECT * FROM students", new BeanPropertyRowMapper<Student>(Student.class));
		
		for (Student stud : allStudents)
		{
			List<Course> courses = retrieveCoursesByStudentIDViaDB(Integer.valueOf(stud.getId()));
			initCourseStepsViaDB(courses);
			stud.setCourses(courses);
		}
		
		logger.debug("retrieveAllStudentsViaDB() results: " + allStudents);
		
		return allStudents;
	}
	
	
	// helpers for: retrieveAllGuestLecturers()
	private List<GuestLecturer> retrieveAllGuestsViaDB()
	{
		// allStudents are intially without courses and courses later in turn without
		// coursesteps
		List<GuestLecturer> allGuests = jdbcTemplate.query("SELECT * FROM guestlecturers", new BeanPropertyRowMapper<GuestLecturer>(GuestLecturer.class));
		
		for (GuestLecturer guestLecturer : allGuests)
		{
			List<Course> courses = retrieveCoursesByGuestIDViaDB(Integer.valueOf(guestLecturer.getId()));
			initCourseStepsViaDB(courses);
			guestLecturer.setCourses(courses);
		}
		
		logger.debug("retrieveAllStudentsViaDB() results: " + allGuests);
		
		return allGuests;
	}
	
	
	
	
	
	
	// helpers for: retrieveCourse()
	private Course retrieveCourseByIDsViaDB(int studentID, int courseID)
	{
		List<Course> studentsCourse = jdbcTemplate.query(
				"SELECT * FROM registered_for rf INNER JOIN courses c ON rf.course_id = c.id WHERE rf.student_id=" + studentID + " AND course_id=" + courseID,
				new BeanPropertyRowMapper<Course>(Course.class));
		if ((studentsCourse == null) || (studentsCourse.size() != 1)) return null;
		
		initCourseStepsViaDB(studentsCourse);
		logger.debug("retrieveCourseByIDsViaDB() results: " + studentsCourse);
		
		return studentsCourse.get(0);
	}
	
	
	// helpers for: addCourseToCurriculum()
	private void insertNewCourse(Course course) throws DataAccessException
	{
		// since name is UNIQUE in courses, we should try to prevent duplicates
		jdbcTemplate.update("INSERT INTO courses (name, description) VALUES ('" + course.getName() + "', '" + course.getDescription() + "');");
	}
	
	
	
	// returns course ID if pre-existing course, -1 otherwise
	private int getExistingCourseID(Course course) throws DataAccessException
	{
		List<String> matches = jdbcTemplate.queryForList("SELECT id FROM courses WHERE name ='" + course.getName() + "';", String.class);
		logger.info("getExistingCourseID() matches: " + matches);
		if (matches.size() != 1) return -1;
		logger.info("getExistingCourseID() getIntegerID: " + getIntegerID(matches.get(0)));
		return getIntegerID(matches.get(0));
	}
	
	
	private int setNewCourseID(Course course)
	{
		// name is UNIQUE in courses TABLE
		String courseIDString = jdbcTemplate.queryForObject("SELECT id FROM courses WHERE name='" + course.getName() + "';", String.class);
		int cID = getIntegerID(courseIDString);
		course.setId(courseIDString);
		return cID;
	}
	
	private void insertCourseSteps(int cID, Course course) throws DataAccessException
	{
		int stepID = 1;
		for (String step : course.getSteps())
		{
			jdbcTemplate.update("INSERT INTO coursesteps (course_id, step_id, description) VALUES (+" + cID + ", " + stepID + ", '" + step + "');");
			stepID++;
		}
	}
	
	
	
	private boolean bookLodgingsForGuestLecturer(int gID)
	{
		// really all of this should be transactional and stuff but for tipping my toes
		// into the waters of communicating REST services, this will have to do for now
		List<GuestLecturer> guests = jdbcTemplate.query("SELECT * FROM guestlecturers gl WHERE gl.id=" + gID + ";",
				new BeanPropertyRowMapper<GuestLecturer>(GuestLecturer.class));
		if ((guests == null) || (guests.size() != 1)) return false;
		GuestLecturer guest = guests.get(0);
		
		Pair<LocalDate, LocalDate> startAndEndDates = createStartAndEndDates();
		ReservationDTO fakeReservation = new ReservationDTO(guest.getTitle(), guest.getFirstName(), guest.getLastName(), "4 Seasons",
				startAndEndDates.getFirst(), startAndEndDates.getSecond());
		
		reactiveProducerService.send(fakeReservation);
		
		return true;
	}
	
	private Pair<LocalDate, LocalDate> createStartAndEndDates()
	{
		SecureRandom secureRandom = new SecureRandom();
		long startEpochDay = LocalDate.of(2022, 3, 16).toEpochDay();
		long endEpochDay = LocalDate.of(2022, 12, 16).toEpochDay();
		long randomStartDate = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
		LocalDate startDate = LocalDate.ofEpochDay(randomStartDate);
		int randomStayDurationInDays = secureRandom.nextInt(20, 150);
		LocalDate endDate = startDate.plusDays(randomStayDurationInDays);
		return Pair.of(startDate, endDate);
	}
	
	
}
