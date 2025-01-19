package com.streamingbot.discordservice.services.impl;

import com.streamingbot.discordservice.models.Server;
import com.streamingbot.discordservice.repositories.ServerRepository;
import com.streamingbot.discordservice.services.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ServerServiceImpl implements ServerService {
    
    private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);
    private final ServerRepository serverRepository;
    private final UUIDGeneratorImpl uuidGeneratorImpl;


    public ServerServiceImpl(ServerRepository serverRepository, UUIDGeneratorImpl uuidGeneratorImpl) {
        this.serverRepository = serverRepository;
        this.uuidGeneratorImpl = uuidGeneratorImpl;
    }

    @Override
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    @Override
    public Server getServerById(UUID id) {
        return serverRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Server not found with id: " + id));
    }

    @Override
    public Server createServer(Server server) {
        try {
            if (server.getId() == null) {
                server.setId(uuidGeneratorImpl.Generate());
                logger.info("Generated new UUID: {}", server.getId());
            }
            
            logger.info("Attempting to save server with ID: {}", server.getId());
            Server savedServer = serverRepository.save(server);
            logger.info("Successfully saved server with ID: {}", savedServer.getId());
            return savedServer;
            
        } catch (Exception e) {
            logger.error("Failed to create server", e);
            throw new RuntimeException("Failed to create server: " + e.getMessage(), e);
        }
    }

    @Override
    public Server updateServer(Server server) {
        if (!serverRepository.existsById(server.getId())) {
            throw new RuntimeException("Server not found with id: " + server.getId());
        }
        return serverRepository.save(server);
    }

    @Override
    public void deleteServer(UUID id) {
        if (!serverRepository.existsById(id)) {
            throw new RuntimeException("Server not found with id: " + id);
        }
        serverRepository.deleteById(id);
    }

    @Override
    public void deleteServersByUserId(String userId) {
        logger.info("Deleting all servers for userId: {}", userId);
        try {
            List<Server> servers = serverRepository.findByUserId(userId);
            logger.debug("Found {} servers to delete for userId: {}", servers.size(), userId);
            
            serverRepository.deleteByUserId(userId);
            logger.info("Successfully deleted all servers for userId: {}", userId);
        } catch (Exception e) {
            logger.error("Failed to delete servers for userId: {}", userId, e);
            throw new RuntimeException("Failed to delete servers: " + e.getMessage());
        }
    }
} 