package com.streamingbot.discordservice.repositories;

import com.streamingbot.discordservice.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    @Query("SELECT s FROM Server s WHERE s.ownerId = :userId")
    List<Server> findByUserId(@Param("userId") String userId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Server s WHERE s.ownerId = :userId")
    void deleteByUserId(@Param("userId") String userId);
} 