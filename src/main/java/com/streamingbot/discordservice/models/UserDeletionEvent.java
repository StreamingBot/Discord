package com.streamingbot.discordservice.models;

public record UserDeletionEvent(
    String userId,
    String userEmail,
    long timestamp
) {} 