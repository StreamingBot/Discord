package com.streamingbot.discordservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "servers")
public class Server implements IServer {
    @Id
    private UUID id;
    private UUID ownerId;
    private String serverId;

    // Default constructor
    public Server() {}

    // Constructor with fields
    public Server(UUID id, UUID ownerId, String serverId) {
        this.id = id;
        this.ownerId = ownerId;
        this.serverId = serverId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String getServerId() {
        return serverId;
    }

    @Override
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "Server{" +
                "id=" + id +
                ", ownerId=" + ownerId +
                ", serverId='" + serverId + '\'' +
                '}';
    }
}
