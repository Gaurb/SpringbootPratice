package com.example.spring_batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.spring_batch.model.Customer;
import com.example.spring_batch.service.ExternalApiService;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
    private final ExternalApiService externalApiService;

    public CustomerProcessor(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @Override
    public Customer process(Customer customer) {
        try {
            externalApiService.processCustomer(customer);
            return customer;
        } catch (Exception e) {
            // Log the error and skip this item
            System.err.println("Error processing customer " + customer.getId() + ": " + e.getMessage());
            return null; // Returning null will skip this item
        }
    }
} 