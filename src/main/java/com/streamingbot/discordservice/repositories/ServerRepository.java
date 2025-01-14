package com.streamingbot.discordservice.repositories;

import com.streamingbot.discordservice.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {
} 