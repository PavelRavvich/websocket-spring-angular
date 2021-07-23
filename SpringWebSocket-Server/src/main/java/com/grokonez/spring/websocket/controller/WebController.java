package com.grokonez.spring.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.grokonez.spring.websocket.model.Hello;
import com.grokonez.spring.websocket.model.User;

@Controller
public class WebController {

	@MessageMapping("/hello")
	@SendTo("/topic/hi")
	public Hello greeting(User user) {
		return new Hello("Hi, " + user.getName() + "!");
	}

	// Not work find simple solution
	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
	public void send() {
		User user = new User();
		user.setName("Scheduled");
		greeting(user);
	}
}
