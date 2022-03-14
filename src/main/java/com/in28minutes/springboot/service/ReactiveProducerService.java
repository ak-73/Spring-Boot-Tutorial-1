package com.in28minutes.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

import com.in28minutes.springboot.dtos.ReservationDTO;

@Service
@Profile({ "kafka", "rabbitmq" })
public class ReactiveProducerService implements CommandLineRunner
{
	private final Logger log = LoggerFactory.getLogger(ReactiveProducerService.class);
	private final ReactiveKafkaProducerTemplate<String, ReservationDTO> reactiveKafkaProducerTemplate_Reservation;
	
	@Value(value = "${reservation.dto.producer.topic}")
	private String topic;
	
	public ReactiveProducerService(ReactiveKafkaProducerTemplate<String, ReservationDTO> reactiveKafkaProducerTemplate_Reservation)
	{
		this.reactiveKafkaProducerTemplate_Reservation = reactiveKafkaProducerTemplate_Reservation;
	}
	
	public void send(ReservationDTO reservationDTO)
	{
		log.info("send to topic={}, {}={},", topic, ReservationDTO.class.getSimpleName(), reservationDTO);
		//@formatter:off
		reactiveKafkaProducerTemplate_Reservation
				.send(topic, reservationDTO)
				.doOnSuccess(senderResult -> log.info("sent {} offset : {}", reservationDTO, senderResult.recordMetadata().offset()))
				.subscribe();
		//@formatter:on
	}
	
	
	// Just for testing
	// adapted from tutorial
	@Override
	public void run(String... args) throws Exception
	{
		/*
		 * AddressDTO fakeAddress = new AddressDTO("Foo Street", 7, 5555555, "Essen");
		 * HotelDTO fakeHotel = new HotelDTO(4, "4 Seasons", fakeAddress);
		 * ReservationDTO fakeReservation = new ReservationDTO(4, null, "Frederick",
		 * "The Great", fakeHotel, 1745, LocalDate.of(2022, 4, 27), LocalDate.of(2022,
		 * 7, 1));
		 * 
		 * send(fakeReservation);
		 */
	}
	
}
