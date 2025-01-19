package com.streamingbot.discordservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.streamingbot.discordservice.models.UserDeletionEvent;

@Service
public class UserDeletionListener {
    private static final Logger logger = LoggerFactory.getLogger(UserDeletionListener.class);
    
    private final CommandService commandService;
    private final ServerService serverService;
    
    public UserDeletionListener(CommandService commandService, ServerService serverService) {
        this.commandService = commandService;
        this.serverService = serverService;
    }
    
    @RabbitListener(queues = "${rabbitmq.queue.user-deletion}")
    public void handleUserDeletion(UserDeletionEvent event) {
        logger.info("Received user deletion event for userId: {}", event.userId());
        try {
            // Delete all user data in parallel services
            commandService.deleteCommandsByUserId(event.userId());
            serverService.deleteServersByUserId(event.userId());
            
            logger.info("Successfully processed deletion for userId: {}", event.userId());
        } catch (Exception e) {
            logger.error("Failed to process user deletion for userId: {}", event.userId(), e);
            throw e; // Let Spring AMQP handle the error and retry
        }
    }
} 