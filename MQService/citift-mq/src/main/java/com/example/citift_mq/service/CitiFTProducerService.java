package com.example.citift_mq.service;

import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CitiFTProducerService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        log.info("Sending message to queue: {}", message);
        jmsTemplate.send("NA.CITIFT.THUB.REMOTE", session -> {
            TextMessage textMessage = session.createTextMessage(message);
            log.info("Message ID: {}", textMessage.getJMSMessageID());
            log.info("Message Correlation ID: {}", textMessage.getJMSCorrelationID());
            log.info("Message Delivery Mode: {}", textMessage.getJMSDeliveryMode());
            log.info("Message Timestamp: {}", textMessage.getJMSTimestamp());
            return textMessage;
        });
        log.info("Message sent successfully");
    }

}
