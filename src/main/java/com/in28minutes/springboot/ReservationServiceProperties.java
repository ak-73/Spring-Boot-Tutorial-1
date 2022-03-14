package com.in28minutes.springboot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@ConfigurationProperties("reservation.dto.producer")
@Component
@Profile({"kafka", "rabbitmq"})
public class ReservationServiceProperties
{
	String topic = "reservation_dto_topic_default";

	public ReservationServiceProperties()
	{
		super();
	}

	public String getTopic()
	{
		return topic;
	}

	public void setTopic(String topic)
	{
		this.topic = topic;
	}
}
