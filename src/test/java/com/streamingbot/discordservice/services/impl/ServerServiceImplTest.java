package com.streamingbot.discordservice.services.impl;

import com.streamingbot.discordservice.models.Server;
import com.streamingbot.discordservice.repositories.ServerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServerServiceImplTest {

    @Mock
    private ServerRepository serverRepository;

    @Mock
    private UUIDGeneratorImpl uuidGenerator;

    @InjectMocks
    private ServerServiceImpl serverService;

    private Server testServer;
    private UUID testId;
    private UUID testOwnerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testId = UUID.randomUUID();
        testOwnerId = UUID.randomUUID();
        testServer = new Server();
        testServer.setId(testId);
        testServer.setOwnerId(testOwnerId);
        testServer.setServerId("123456");
    }

    @Test
    void getAllServers_ShouldReturnAllServers() {
        List<Server> expectedServers = Arrays.asList(testServer);
        when(serverRepository.findAll()).thenReturn(expectedServers);

        List<Server> actualServers = serverService.getAllServers();

        assertEquals(expectedServers, actualServers);
        verify(serverRepository).findAll();
    }

    @Test
    void getServerById_WhenServerExists_ShouldReturnServer() {
        when(serverRepository.findById(testId)).thenReturn(Optional.of(testServer));

        Server actualServer = serverService.getServerById(testId);

        assertEquals(testServer, actualServer);
    }

    @Test
    void getServerById_WhenServerDoesNotExist_ShouldThrowException() {
        when(serverRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> serverService.getServerById(testId));
    }

    @Test
    void createServer_WithNullId_ShouldGenerateIdAndSave() {
        Server newServer = new Server();
        when(uuidGenerator.Generate()).thenReturn(testId);
        when(serverRepository.save(any(Server.class))).thenReturn(testServer);

        Server createdServer = serverService.createServer(newServer);

        assertEquals(testId, createdServer.getId());
        verify(serverRepository).save(newServer);
    }

    @Test
    void updateServer_WhenServerExists_ShouldUpdate() {
        when(serverRepository.existsById(testId)).thenReturn(true);
        when(serverRepository.save(testServer)).thenReturn(testServer);

        Server updatedServer = serverService.updateServer(testServer);

        assertEquals(testServer, updatedServer);
        verify(serverRepository).save(testServer);
    }

    @Test
    void updateServer_WhenServerDoesNotExist_ShouldThrowException() {
        when(serverRepository.existsById(testId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serverService.updateServer(testServer));
    }

    @Test
    void deleteServer_WhenServerExists_ShouldDelete() {
        when(serverRepository.existsById(testId)).thenReturn(true);
        doNothing().when(serverRepository).deleteById(testId);

        assertDoesNotThrow(() -> serverService.deleteServer(testId));
        verify(serverRepository).deleteById(testId);
    }

    @Test
    void deleteServer_WhenServerDoesNotExist_ShouldThrowException() {
        when(serverRepository.existsById(testId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> serverService.deleteServer(testId));
    }

    @Test
    void deleteServersByUserId_ShouldDeleteAllUserServers() {
        String userId = "testUserId";
        List<Server> userServers = Arrays.asList(testServer);
        when(serverRepository.findByUserId(userId)).thenReturn(userServers);
        doNothing().when(serverRepository).deleteByUserId(userId);

        assertDoesNotThrow(() -> serverService.deleteServersByUserId(userId));
        verify(serverRepository).deleteByUserId(userId);
    }
} 