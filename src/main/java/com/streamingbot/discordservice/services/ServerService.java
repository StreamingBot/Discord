package com.streamingbot.discordservice.services;

import com.streamingbot.discordservice.models.Server;
import java.util.List;
import java.util.UUID;

public interface ServerService {
    List<Server> getAllServers();
    Server getServerById(UUID id);
    Server createServer(Server server);
    Server updateServer(Server server);
    void deleteServer(UUID id);
} 