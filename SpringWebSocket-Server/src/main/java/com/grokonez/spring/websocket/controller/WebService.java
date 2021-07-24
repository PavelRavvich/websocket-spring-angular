package com.grokonez.spring.websocket.controller;

import com.grokonez.spring.websocket.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class WebService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void sendFromFirstProducer() {
        simpMessagingTemplate.convertAndSend("/stream/newOrder" + "/2",
                new Order("sendFromFirstProducer only for consumers with producerId 2"));
    }
}
