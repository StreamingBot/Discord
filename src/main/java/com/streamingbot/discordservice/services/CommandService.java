package com.streamingbot.discordservice.services;

import com.streamingbot.discordservice.models.Command;
import java.util.List;
import java.util.UUID;

public interface CommandService {
    List<Command> getAllCommands();
    Command getCommandById(UUID id);
    List<Command> getCommandsByServerId(UUID serverId);
    Command createCommand(Command command);
    Command updateCommand(Command command);
    void deleteCommand(UUID id);
} 