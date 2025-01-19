package com.streamingbot.discordservice.services.impl;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UUIDGeneratorImplTest {

    private final UUIDGeneratorImpl uuidGenerator = new UUIDGeneratorImpl();

    @Test
    void Generate_ShouldReturnValidUUID() {
        UUID uuid = uuidGenerator.Generate();
        
        assertNotNull(uuid);
    }

    @Test
    void Generate_ShouldReturnDifferentUUIDs() {
        UUID uuid1 = uuidGenerator.Generate();
        UUID uuid2 = uuidGenerator.Generate();
        
        assertNotEquals(uuid1, uuid2);
    }
} 