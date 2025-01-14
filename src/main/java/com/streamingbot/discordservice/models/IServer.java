package com.streamingbot.discordservice.models;

import java.util.UUID;

public interface IServer {
    UUID getId();
    void setId(UUID id);
    
    UUID getOwnerId();
    void setOwnerId(UUID ownerId);
    
    String getServerId();
    void setServerId(String serverId);
}
