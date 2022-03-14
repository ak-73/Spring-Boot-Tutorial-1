package com.in28minutes.springboot.service;

import java.util.List;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.model.GuestLecturer;
import com.in28minutes.springboot.model.Student;


public interface IStudentService
{
	
	List<Student> retrieveAllStudents();
	
	Student retrieveStudent(String studentId);
	
	List<Course> retrieveCourses(String studentId);
	
	Course retrieveCourse(String studentId, String courseId);
	
	Course addCourseToCurriculum(String studentId, Course course);

	List<GuestLecturer> retrieveAllGuestLecturers();

	Course registerGuestLecturerForCourse(String guestId, Course newCourse);
	
}
