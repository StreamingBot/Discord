package com.streamingbot.discordservice.services;

import com.streamingbot.discordservice.models.UserDeletionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserDeletionListener {
    private static final Logger logger = LoggerFactory.getLogger(UserDeletionListener.class);
    
    private final ServerService serverService;
    private final CommandService commandService;

    public UserDeletionListener(ServerService serverService, CommandService commandService) {
        this.serverService = serverService;
        this.commandService = commandService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.user-deletion}")
    public void handleUserDeletion(UserDeletionEvent event) {
        logger.info("Received user deletion event for user: {}", event.userId());

        try {
            serverService.deleteServersByUserId(event.userId());
        } catch (Exception e) {
            logger.error("Failed to delete servers for user {}: {}", event.userId(), e.getMessage());
        }

        try {
            commandService.deleteCommandsByUserId(event.userId());
        } catch (Exception e) {
            logger.error("Failed to delete commands for user {}: {}", event.userId(), e.getMessage());
        }
    }
} 