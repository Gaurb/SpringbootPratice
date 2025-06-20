package com.example.spring_batch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.spring_batch.model.Customer;

@Service
public class ExternalApiService {
    private final WebClient webClient;

    public ExternalApiService(WebClient.Builder webClientBuilder, @Value("${api.base.url}") String baseUrl) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public void processCustomer(Customer customer) {
        webClient.post()
                .uri("/customers")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Void.class)
                .block(); // Blocking for demonstration - consider using reactive approach in production
    }
} 