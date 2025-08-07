package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PrivateChatController {
    private static final Logger logger = LoggerFactory.getLogger(PrivateChatController.class);
    private final SimpMessagingTemplate messagingTemplate;

    public PrivateChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/private-message")
    public void sendPrivateMessage(@Payload PrivateMessage privateMessage) {
        logger.info("Received private message: {}", privateMessage);
        
        // Send to recipient
        String recipientDestination = "/user/" + privateMessage.getTo() + "/queue/private";
        logger.info("Sending to recipient at: {}", recipientDestination);
        messagingTemplate.convertAndSendToUser(
            privateMessage.getTo(),
            "/queue/private",
            privateMessage
        );
        
        // Send back to sender
        String senderDestination = "/user/" + privateMessage.getFrom() + "/queue/private";
        logger.info("Sending back to sender at: {}", senderDestination);
        messagingTemplate.convertAndSendToUser(
            privateMessage.getFrom(),
            "/queue/private",
            privateMessage
        );
    }
} 