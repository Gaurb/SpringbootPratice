package com.example.thub_mq.service;

import com.ibm.mq.*;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ThubProducerService {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        log.info("Sending message to queue: {}", message);
        jmsTemplate.send("NA.THUB.CITIFT.ACK.REMOTE", session -> {
            TextMessage textMessage = session.createTextMessage(message);
            log.info("Message ID: {}", textMessage.getJMSMessageID());
            log.info("Message Correlation ID: {}", textMessage.getJMSCorrelationID());
            log.info("Message Delivery Mode: {}", textMessage.getJMSDeliveryMode());
            log.info("Message Timestamp: {}", textMessage.getJMSTimestamp());
            return textMessage;
        });
        log.info("Message sent successfully");
    }

    private MQQueueManager createQueueManager() throws MQException {
        // Implement your logic to create and return a MQQueueManager instance
        // This is just a placeholder method
        String host = "localhost";
        int port = 1424;

        MQEnvironment.hostname = host;
        MQEnvironment.port = port;
        MQEnvironment.channel = "DEV.ADMIN.SVRCONN";
        MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES_CLIENT);
        MQEnvironment.properties.put(MQC.USER_ID_PROPERTY, "admin");
        MQEnvironment.properties.put(MQC.PASSWORD_PROPERTY, "passw0rd");
        return new MQQueueManager("THUB"); // Replace with actual MQQueueManager creation logic
    }

    public  void pushMessageToRemote(String message) {
        try {
            MQQueueManager queueManager = createQueueManager();
            var openOptions = MQC.MQOO_OUTPUT | MQC.MQOO_SET_IDENTITY_CONTEXT;
            var queue = queueManager.accessQueue("NA.THUB.CITIFT.ACK.REMOTE", openOptions,null,null,null);
            var msg= new MQMessage();
            msg.format= MQC.MQFMT_STRING;
            msg.writeString(message);

            var putOptions = new MQPutMessageOptions();
            queue.put(msg, putOptions);
            log.info("Message pushed to remote successfully");
        } catch (MQException e) {
            log.error("Error pushing message to remote: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to push message to remote", e);
        } catch (IOException e) {
            log.error("IO Error while pushing message to remote: {}", e.getMessage(), e);
            throw new RuntimeException("IO Error while pushing message to remote", e);
        }
    }
}
