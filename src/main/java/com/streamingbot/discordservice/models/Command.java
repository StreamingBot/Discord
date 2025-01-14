package com.streamingbot.discordservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "commands")
public class Command implements ICommand {
    @Id
    private UUID id;
    private String channelId;
    private UUID serverId;
    private String command;
    private String role;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public UUID getServerId() {
        return serverId;
    }

    @Override
    public void setServerId(UUID serverId) {
        this.serverId = serverId;
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }
}
