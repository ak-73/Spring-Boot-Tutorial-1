package com.in28minutes.springboot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student
{
	private String id;
	private String name;
	private String description;
	private List<Course> courses;
	
	public Student()
	{
		super();
		this.id = "-1";
		this.name = "Error";
		this.description = "This should never be visible.";
		this.courses = new ArrayList<Course>();
	}
	
	public Student(String id, String name, String description)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.courses = new ArrayList<Course>();
	}
	
	public Student(String id, String name, String description, List<Course> courses)
	{
		super();
		this.id = id;
		this.name = name;
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
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
		return Objects.hash(id);
	}
	
	
	
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Student other = (Student) obj;
		if (id == null)
		{
			if (other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		return true;
	}




	@Override
	public String toString()
	{
		return "Student [id=" + id + ", name=" + name + ", description=" + description + ", courses=" + courses + "]";
	}
	
	
}
