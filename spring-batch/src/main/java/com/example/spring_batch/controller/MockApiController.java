package com.example.spring_batch.controller;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring_batch.model.Customer;

@RestController
@RequestMapping("/mock-api")
public class MockApiController {
    private final Random random = new Random();
    
    @PostMapping("/customers")
    public void processCustomer(@RequestBody Customer customer) {
        System.out.println("Mock API received customer: " + customer.getName());
        
        // Simulate random failures (30% chance)
        if (random.nextDouble() < 0.3) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Simulated error processing customer: " + customer.getName());
        }
        
        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 