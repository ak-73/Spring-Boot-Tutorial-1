package com.in28minutes.springboot;

import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;

import com.in28minutes.springboot.dtos.ReservationDTO;

import reactor.kafka.sender.SenderOptions;

@Configuration
@Profile("kafka")
public class ReactiveKafkaProducerConfig
{
	@Bean
	public ReactiveKafkaProducerTemplate<String, ReservationDTO> reactiveKafkaProducerTemplate_Reservation(KafkaProperties properties)
	{
		Map<String, Object> props = properties.buildProducerProperties();
		return new ReactiveKafkaProducerTemplate<String, ReservationDTO>(SenderOptions.create(props));
	}

	
}
