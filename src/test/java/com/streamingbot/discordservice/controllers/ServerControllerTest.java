package com.streamingbot.discordservice.controllers;

import com.streamingbot.discordservice.models.Server;
import com.streamingbot.discordservice.services.ServerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServerControllerTest {

    @Mock
    private ServerService serverService;

    @InjectMocks
    private ServerController serverController;

    private Server testServer;
    private UUID testId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testId = UUID.randomUUID();
        testServer = new Server();
        testServer.setId(testId);
        testServer.setOwnerId(UUID.randomUUID());
        testServer.setServerId("123456");
    }

    @Test
    void getAllServers_ShouldReturnListOfServers() {
        List<Server> expectedServers = Arrays.asList(testServer);
        when(serverService.getAllServers()).thenReturn(expectedServers);

        List<Server> actualServers = serverController.getAllServers();

        assertEquals(expectedServers, actualServers);
        verify(serverService).getAllServers();
    }

    @Test
    void getServerById_WhenServerExists_ShouldReturnServer() {
        when(serverService.getServerById(testId)).thenReturn(testServer);

        ResponseEntity<Server> response = serverController.getServerById(testId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testServer, response.getBody());
    }

    @Test
    void getServerById_WhenServerDoesNotExist_ShouldReturnNotFound() {
        when(serverService.getServerById(testId)).thenThrow(new RuntimeException("Server not found"));

        ResponseEntity<Server> response = serverController.getServerById(testId);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void createServer_ShouldReturnCreatedServer() {
        when(serverService.createServer(testServer)).thenReturn(testServer);

        Server createdServer = serverController.createServer(testServer);

        assertEquals(testServer, createdServer);
        verify(serverService).createServer(testServer);
    }

    @Test
    void updateServer_ShouldReturnUpdatedServer() {
        when(serverService.updateServer(testServer)).thenReturn(testServer);

        Server updatedServer = serverController.updateServer(testId, testServer);

        assertEquals(testId, updatedServer.getId());
        assertEquals(testServer, updatedServer);
        verify(serverService).updateServer(testServer);
    }

    @Test
    void deleteServer_ShouldReturnOkResponse() {
        doNothing().when(serverService).deleteServer(testId);

        ResponseEntity<Void> response = serverController.deleteServer(testId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(serverService).deleteServer(testId);
    }
} 