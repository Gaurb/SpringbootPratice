package com.example.mqspring.controller;

import com.example.mqspring.service.MQProducerService;
import com.example.mqspring.service.MQBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mq")
public class MQController {

    @Autowired
    private MQProducerService producerService;

    @Autowired
    private MQBrowserService browserService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        producerService.sendMessage(message);
        return "Message sent successfully: " + message;
    }

    @GetMapping("/browse")
    public List<String> browseMessages() {
        return browserService.browseMessages("DEV.QUEUE.1");
    }
} 