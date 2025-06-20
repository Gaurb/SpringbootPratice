package com.example.citift_mq.controller;

import com.example.citift_mq.service.CitiFTProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/citi")
public class CitiFTController {

    @Autowired
    private CitiFTProducerService producerService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return "Message sent successfully: " + message;
    }
} 