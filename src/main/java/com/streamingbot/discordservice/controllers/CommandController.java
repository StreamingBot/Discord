package com.streamingbot.discordservice.controllers;

import com.streamingbot.discordservice.models.Command;
import com.streamingbot.discordservice.services.CommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/commands")
public class CommandController {
    
    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping
    public List<Command> getAllCommands() {
        return commandService.getAllCommands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable UUID id) {
        try {
            Command command = commandService.getCommandById(id);
            return ResponseEntity.ok(command);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/server/{serverId}")
    public List<Command> getCommandsByServerId(@PathVariable String serverId) {
        return commandService.getCommandsByServerId(serverId);
    }

    @PostMapping
    public Command createCommand(@RequestBody Command command) {
        return commandService.createCommand(command);
    }

    @PutMapping("/{id}")
    public Command updateCommand(@PathVariable UUID id, @RequestBody Command command) {
        command.setId(id);
        return commandService.updateCommand(command);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable UUID id) {
        commandService.deleteCommand(id);
        return ResponseEntity.ok().build();
    }
} 