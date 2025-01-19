package com.streamingbot.discordservice.services;

import com.streamingbot.discordservice.models.UserDeletionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class UserDeletionListenerTest {

    @Mock
    private ServerService serverService;

    @Mock
    private CommandService commandService;

    @InjectMocks
    private UserDeletionListener userDeletionListener;

    private UserDeletionEvent testEvent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testEvent = new UserDeletionEvent("testUserId", "test@email.com", System.currentTimeMillis());
    }

    @Test
    void handleUserDeletion_ShouldDeleteUserData() {
        doNothing().when(serverService).deleteServersByUserId(testEvent.userId());
        doNothing().when(commandService).deleteCommandsByUserId(testEvent.userId());

        assertDoesNotThrow(() -> userDeletionListener.handleUserDeletion(testEvent));

        verify(serverService).deleteServersByUserId(testEvent.userId());
        verify(commandService).deleteCommandsByUserId(testEvent.userId());
    }

    @Test
    void handleUserDeletion_WhenServerServiceFails_ShouldStillDeleteCommands() {
        doThrow(new RuntimeException("Server deletion failed"))
            .when(serverService).deleteServersByUserId(testEvent.userId());
        doNothing().when(commandService).deleteCommandsByUserId(testEvent.userId());

        assertDoesNotThrow(() -> userDeletionListener.handleUserDeletion(testEvent));

        verify(commandService).deleteCommandsByUserId(testEvent.userId());
    }

    @Test
    void handleUserDeletion_WhenCommandServiceFails_ShouldStillDeleteServers() {
        doNothing().when(serverService).deleteServersByUserId(testEvent.userId());
        doThrow(new RuntimeException("Command deletion failed"))
            .when(commandService).deleteCommandsByUserId(testEvent.userId());

        assertDoesNotThrow(() -> userDeletionListener.handleUserDeletion(testEvent));

        verify(serverService).deleteServersByUserId(testEvent.userId());
    }
} 