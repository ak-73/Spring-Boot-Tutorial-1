package com.in28minutes.springboot.dtos;

import org.springframework.context.annotation.Profile;

@Profile({"kafka", "rabbitmq"})
public class HotelDTO
{
	private int id;	
	private String name;
	AddressDTO address;
	
	public HotelDTO()
	{
		super();
	}
	
	public HotelDTO(int id, String name, AddressDTO address)
	{
		super();
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public AddressDTO getAddress()
	{
		return address;
	}

	public void setAddress(AddressDTO address)
	{
		this.address = address;
	}
}
