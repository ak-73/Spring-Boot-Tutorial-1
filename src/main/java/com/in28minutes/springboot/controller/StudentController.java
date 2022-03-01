package com.in28minutes.springboot.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.springboot.model.Course;
import com.in28minutes.springboot.model.Student;
import com.in28minutes.springboot.service.IStudentService;

@RestController
public class StudentController
{
	
	@Autowired
	private IStudentService studentService;
	
	@GetMapping("/students/{studentId}")
	public Student retrieveStudent(@PathVariable String studentId)
	{
		return studentService.retrieveStudent(studentId);
	}
	
	@GetMapping("/students")
	public List<Student> retrieveAlleStudents()
	{
		return studentService.retrieveAllStudents();
	}
	
	@GetMapping("/students/{studentId}/courses")
	public List<Course> retrieveCoursesForStudent(@PathVariable String studentId)
	{
		return studentService.retrieveCourses(studentId);
	}
	
	@GetMapping("/students/{studentId}/courses/{courseId}")
	public Course retrieveDetailsForCourse(@PathVariable String studentId, @PathVariable String courseId)
	{
		return studentService.retrieveCourse(studentId, courseId);
	}
	
	@PostMapping("/students/{studentId}/courses")
	public ResponseEntity<Void> registerStudentForCourse(@PathVariable String studentId, @RequestBody Course newCourse)
	{		
		Course course = studentService.addCourseToCurriculum(studentId, newCourse);
		
		if (course == null) return ResponseEntity.noContent().build();
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(course.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
}
