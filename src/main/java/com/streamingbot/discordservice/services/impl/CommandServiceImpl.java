package com.streamingbot.discordservice.services.impl;

import com.streamingbot.discordservice.models.Command;
import com.streamingbot.discordservice.repositories.CommandRepository;
import com.streamingbot.discordservice.services.CommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CommandServiceImpl implements CommandService {
    
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
    public List<Command> getCommandsByServerId(UUID serverId) {
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
} 