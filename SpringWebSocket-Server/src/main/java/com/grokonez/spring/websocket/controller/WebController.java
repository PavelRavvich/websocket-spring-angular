package com.grokonez.spring.websocket.controller;

import com.grokonez.spring.websocket.model.Order;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.*;


@Controller
public class WebController {

	private final SimpMessagingTemplate simpMessagingTemplate;

	// DB Mock just for test.
	private Map<Integer, List<Integer>> producerToConsumersStore = new HashMap<>();

	public WebController(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@PostConstruct
	public void postConstruct() {
		List<Integer> firstProducer = new ArrayList<>();
		firstProducer.add(10);
		firstProducer.add(11);
		producerToConsumersStore.put(1, firstProducer);

		List<Integer> secondProducer = new ArrayList<>();
		secondProducer.add(20);
		secondProducer.add(21);
		producerToConsumersStore.put(2, secondProducer);
	}

//	@MessageMapping("/newOrders/{producerId}/{consumerId}")
//	public void greeting(@DestinationVariable Integer producerId,
//						 @DestinationVariable Integer consumerId) {
//		// Somehow bind consumer to stream dedicated to producerId (Each producer should send only own subscribers)
//		System.out.println(producerId);
//		System.out.println(consumerId);
//	}

	@Scheduled(fixedDelay = 3000, initialDelay = 1000)
	public void sendFromFirstProducer() {
		simpMessagingTemplate.convertAndSend("/stream/newOrder" + "/1",
				new Order("sendFromFirstProducer only for consumers with ids 10 & 11"));
	}

	@Scheduled(fixedDelay = 2000, initialDelay = 1000)
	public void sendFromSecondProducer() {
		simpMessagingTemplate.convertAndSend("/stream/newOrder" + "/2",
				new Order("sendFromSecondProducer only for consumers with ids 20 & 21"));
	}

}
