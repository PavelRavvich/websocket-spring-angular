package com.grokonez.spring.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.grokonez.spring.websocket.model.Hello;
import com.grokonez.spring.websocket.model.User;

@Controller
public class WebController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	private String room = "my_room";

	@MessageMapping("/token")
	public void greeting(@DestinationVariable String room) {
		System.out.println(room);
	}

	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
	public void send() {
		simpMessagingTemplate.convertAndSend("/stream/newOrder", new Hello("Hi from server"));
	}
}
