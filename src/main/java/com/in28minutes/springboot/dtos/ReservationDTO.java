package com.in28minutes.springboot.dtos;

import java.time.LocalDate;

import org.springframework.context.annotation.Profile;

@Profile({"kafka", "rabbitmq"})
public class ReservationDTO
{
	String title;
	String firstName;
	String lastName;	
	String hotelName;
	LocalDate checkInDate;
	LocalDate checkOutDate;
	
	public ReservationDTO()
	{
		super();
	}

	public ReservationDTO(String title, String firstName, String lastName, String hotelName, LocalDate checkInDate, LocalDate checkOutDate)
	{
		super();
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.hotelName = hotelName;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
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

	public String getHotelName()
	{
		return hotelName;
	}

	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}

	public LocalDate getCheckInDate()
	{
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate)
	{
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate()
	{
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate)
	{
		this.checkOutDate = checkOutDate;
	}

	@Override
	public String toString()
	{
		return "ReservationDTO [title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", hotelName=" + hotelName + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate + "]";
	}
	


	
}
