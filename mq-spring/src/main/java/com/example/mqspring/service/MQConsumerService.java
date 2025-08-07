package com.example.mqspring.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import jakarta.jms.TextMessage;

@Service
@Slf4j
public class MQConsumerService {

    // @JmsListener(destination = "DEV.QUEUE.1")
    public void receiveMessage(Object message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String messageText = textMessage.getText();
                log.info("Received TextMessage: {}", messageText);
                log.info("Message ID: {}", textMessage.getJMSMessageID());
                log.info("Message Correlation ID: {}", textMessage.getJMSCorrelationID());
                log.info("Message Delivery Mode: {}", textMessage.getJMSDeliveryMode());
                log.info("Message Timestamp: {}", textMessage.getJMSTimestamp());
            } else if (message instanceof String) {
                log.info("Received String message: {}", message);
            } else {
                log.info("Received message of type: {}", message.getClass().getName());
                log.info("Message content: {}", message);
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }
} 