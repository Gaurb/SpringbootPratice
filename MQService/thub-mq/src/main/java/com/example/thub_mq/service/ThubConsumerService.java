package com.example.thub_mq.service;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ThubConsumerService {

    private final String ACK_PREFIX = "ACK_";
    private final String NACK_PREFIX = "IMAD";

    @Autowired
    private ThubProducerService thubProducerService;




//    @JmsListener(destination = "NA.CITIFT.THUB.LISTEN")
    public void receiveMessage(Object message) {
        try {
           String msg= getMessageContent(message);
           if(msg.startsWith(ACK_PREFIX)){
                log.info("Received ACK message: {}", msg);
              } else if(msg.startsWith(NACK_PREFIX)){
                log.info("Received NACK message: {}", msg);
//                thubProducerService.sendMessage("BACK SUCCESSFULLY" );
              } else {
                log.info("Received message: {}", msg);
           }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    private String getMessageContent(Object message) throws JMSException {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String messageText = textMessage.getText();
            log.info("Received TextMessage: {}", messageText);
            log.info("Message ID: {}", textMessage.getJMSMessageID());
            log.info("Message Correlation ID: {}", textMessage.getJMSCorrelationID());
            log.info("Message Delivery Mode: {}", textMessage.getJMSDeliveryMode());
            log.info("Message Timestamp: {}", textMessage.getJMSTimestamp());
            return messageText;
        } else if (message instanceof String) {
            log.info("Received String message: {}", message);
            return (String) message;
        } else {
            log.info("Received message of type: {}", message.getClass().getName());
            log.info("Message content: {}", message);
            return message.toString();
        }
    }


}
