package com.example.mqspring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MQService {
    private static final Logger logger = LoggerFactory.getLogger(MQService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${mq.queue.citift.thub.listen}")
    private String inputQueue;

    @Value("${mq.queue.thub.citift.ack}")
    private String outputQueue;

    /**
     * Listen for messages from input queue
     * @param message The received message
     */
    @JmsListener(destination = "${mq.queue.citift.thub.listen}")
    public void receiveMessage(String message) {
        logger.info("Received message: {}", message);
        
        try {
            // Process the message
            String response = "Processed: " + message;
            
            // Send response to output queue
            sendResponse(response);
            
        } catch (Exception e) {
            logger.error("Error processing message: {}", message, e);
        }
    }

    /**
     * Send response message to output queue
     * @param message The response message to send
     */
    public void sendResponse(String message) {
        logger.info("Sending response: {}", message);
        jmsTemplate.convertAndSend(outputQueue, message);
    }
} 