package com.in28minutes.springboot.dtos;

import org.springframework.context.annotation.Profile;

@Profile({"kafka", "rabbitmq"})
public class AddressDTO
{
	String streetName;
	int streetNumber;
	int postalCode;
	String postalRegionName;
	String state;
	String country;
	
	public AddressDTO()
	{
		super();
	}
	
	public AddressDTO(String streetName, int streetNumber, int postalCode, String postalRegionName)
	{
		super();
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.postalCode = postalCode;
		this.postalRegionName = postalRegionName;
		state = "NRW";
		country = "Germany";
	}

	public String getStreetName()
	{
		return streetName;
	}

	public void setStreetName(String streetName)
	{
		this.streetName = streetName;
	}

	public int getStreetNumber()
	{
		return streetNumber;
	}

	public void setStreetNumber(int streetNumber)
	{
		this.streetNumber = streetNumber;
	}

	public int getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(int postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getPostalRegionName()
	{
		return postalRegionName;
	}

	public void setPostalRegionName(String postalRegionName)
	{
		this.postalRegionName = postalRegionName;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}
}
