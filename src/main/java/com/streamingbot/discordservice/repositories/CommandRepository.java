package com.streamingbot.discordservice.repositories;

import com.streamingbot.discordservice.models.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<Command, UUID> {
    @Query("SELECT c FROM Command c WHERE c.serverId = :serverId")
    List<Command> findByServerId(@Param("serverId") UUID serverId);
} 