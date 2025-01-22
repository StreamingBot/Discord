package com.streamingbot.discordservice.services.impl;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UUIDGeneratorImpl {

    public UUID Generate() {
        return UUID.randomUUID();
    }
}
