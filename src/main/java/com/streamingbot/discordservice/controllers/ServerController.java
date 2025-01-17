package com.streamingbot.discordservice.controllers;

import com.streamingbot.discordservice.models.Server;
import com.streamingbot.discordservice.services.ServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servers")
public class ServerController {
    
    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @GetMapping
    public List<Server> getAllServers() {
        return serverService.getAllServers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Server> getServerById(@PathVariable UUID id) {
        try {
            Server server = serverService.getServerById(id);
            return ResponseEntity.ok(server);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Server createServer(@RequestBody Server server) {
        return serverService.createServer(server);
    }

    @PutMapping("/{id}")
    public Server updateServer(@PathVariable UUID id, @RequestBody Server server) {
        server.setId(id);
        return serverService.updateServer(server);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable UUID id) {
        serverService.deleteServer(id);
        return ResponseEntity.ok().build();
    }
} 