package com.example.thub_mq.controller;

import com.example.thub_mq.service.ThubProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/thub")
public class ThubController {
    @Autowired
    private ThubProducerService producerService;
    @Autowired
    JmsTemplate jmsTemplate;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        producerService.pushMessageToRemote(message);
        return "Message sent successfully: " + message;
    }

//    @GetMapping("/receive")
//    public String receiveMessage() {
//        log.info("Attempting to receive message from queue");
//         // Receive message from the queue
//         // Note: Ensure that the queue name matches the one used in your producer service
//        Object messageContent = jmsTemplate.receiveAndConvert("DEV.QUEUE.1");
//        if (messageContent != null) {
//            log.info("Message received: {}", messageContent);
//            return "Received message: " + messageContent.toString();
//
//        } else {
//            return "No message received";
//        }
//    }
}
