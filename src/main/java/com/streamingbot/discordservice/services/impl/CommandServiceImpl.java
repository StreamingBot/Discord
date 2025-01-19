package com.streamingbot.discordservice.services.impl;

import com.streamingbot.discordservice.models.Command;
import com.streamingbot.discordservice.repositories.CommandRepository;
import com.streamingbot.discordservice.services.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommandServiceImpl implements CommandService {
    
    private static final Logger logger = LoggerFactory.getLogger(CommandServiceImpl.class);
    private final CommandRepository commandRepository;

    public CommandServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    @Override
    public Command getCommandById(UUID id) {
        return commandRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Command not found"));
    }

    @Override
    public List<Command> getCommandsByServerId(String serverId) {
        return commandRepository.findByServerId(serverId);
    }

    @Override
    public Command createCommand(Command command) {
        if (command.getId() == null) {
            command.setId(UUID.randomUUID());
        }
        return commandRepository.save(command);
    }

    @Override
    public Command updateCommand(Command command) {
        if (!commandRepository.existsById(command.getId())) {
            throw new RuntimeException("Command not found");
        }
        return commandRepository.save(command);
    }

    @Override
    public void deleteCommand(UUID id) {
        if (!commandRepository.existsById(id)) {
            throw new RuntimeException("Command not found");
        }
        commandRepository.deleteById(id);
    }

    @Override
    public void deleteCommandsByUserId(String serverId) {
        logger.info("Deleting all commands for userId: {}", serverId);
        try {
            List<Command> commands = commandRepository.findByServerId(serverId);
            logger.debug("Found {} commands to delete for userId: {}", commands.size(), serverId);
            
            commandRepository.deleteByServerId(serverId);
            logger.info("Successfully deleted all commands for userId: {}", serverId);
        } catch (Exception e) {
            logger.error("Failed to delete commands for userId: {}", serverId, e);
            throw new RuntimeException("Failed to delete commands: " + e.getMessage());
        }
    }
} 