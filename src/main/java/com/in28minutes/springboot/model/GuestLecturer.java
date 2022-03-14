package com.in28minutes.springboot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuestLecturer
{
	private String id;
	private String title;
	private String firstName;
	private String lastName;
	private String description;
	private List<Course> courses;
	
	
	public GuestLecturer()
	{
		super();
		this.id = "-1";
		this.title = "Error";
		this.firstName = "Error";
		this.lastName = "Error";
		this.description = "This should never be visible.";
		this.courses = new ArrayList<Course>();
	}

	public GuestLecturer(String id, String title, String firstName, String lastName, String description, List<Course> courses)
	{
		super();
		this.id = id;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.courses = courses;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<Course> getCourses()
	{
		return courses;
	}

	public void setCourses(List<Course> courses)
	{
		this.courses = courses;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(courses, description, firstName, id, lastName, title);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GuestLecturer other = (GuestLecturer) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(description, other.description) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName) && Objects.equals(title, other.title);
	}

	@Override
	public String toString()
	{
		return "GuestLecturer [id=" + id + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", description=" + description
				+ ", courses=" + courses + "]";
	}
	
	
}
