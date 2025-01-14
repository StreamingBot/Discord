package com.streamingbot.discordservice.models;

import java.util.UUID;

public interface ICommand {
    UUID getId();
    void setId(UUID id);
    
    String getChannelId();
    void setChannelId(String channelId);
    
    UUID getServerId();
    void setServerId(UUID serverId);
    
    String getCommand();
    void setCommand(String command);
    
    String getRole();
    void setRole(String role);
}
