package com.in28minutes.springboot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course
{
	private String id;
	private String name;
	private String description;
	private List<String> steps;
	
	public Course()
	{
		super();
		this.id = "-1";
		this.name = "Error";
		this.description = "This should never be visible.";
		this.steps = new ArrayList<String>();
	}
	
	public Course(String id, String name, String description, List<String> steps)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.steps = steps;
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
	
	public List<String> getSteps()
	{
		return steps;
	}
	
	public void setSteps(List<String> steps)
	{
		this.steps = steps;
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
		Course other = (Course) obj;
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
		return "Course [id=" + id + ", name=" + name + ", description=" + description + ", steps=" + steps + "]";
	}
	
	
}
