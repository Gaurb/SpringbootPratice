package com.example.mqspring.service;

import jakarta.jms.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Enumeration;

@Service
@Slf4j
public class MQBrowserService {

    @Autowired
    private ConnectionFactory connectionFactory;

    public List<String> browseMessages(String queueName) {
        List<String> messages = new ArrayList<>();
        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
            
            Queue queue = session.createQueue(queueName);
            QueueBrowser browser = session.createBrowser(queue);
            
            Enumeration<?> enumeration = browser.getEnumeration();
            while (enumeration.hasMoreElements()) {
                Message message = (Message) enumeration.nextElement();
                if (message instanceof TextMessage) {
                    messages.add(((TextMessage) message).getText());
                }
            }
            
            log.info("Found {} messages in queue {}", messages.size(), queueName);
        } catch (JMSException e) {
            log.error("Error browsing messages: {}", e.getMessage(), e);
        }
        return messages;
    }
} 