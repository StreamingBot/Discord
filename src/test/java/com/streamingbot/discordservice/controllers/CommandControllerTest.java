package com.streamingbot.discordservice.controllers;

import com.streamingbot.discordservice.models.Command;
import com.streamingbot.discordservice.services.CommandService;
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

class CommandControllerTest {

    @Mock
    private CommandService commandService;

    @InjectMocks
    private CommandController commandController;

    private Command testCommand;
    private UUID testId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testId = UUID.randomUUID();
        testCommand = new Command();
        testCommand.setId(testId);
        testCommand.setCommand("!test");
        testCommand.setChannelId("123456");
        testCommand.setServerId(UUID.randomUUID());
        testCommand.setRole("admin");
    }

    @Test
    void getAllCommands_ShouldReturnListOfCommands() {
        List<Command> expectedCommands = Arrays.asList(testCommand);
        when(commandService.getAllCommands()).thenReturn(expectedCommands);

        List<Command> actualCommands = commandController.getAllCommands();

        assertEquals(expectedCommands, actualCommands);
        verify(commandService).getAllCommands();
    }

    @Test
    void getCommandById_WhenCommandExists_ShouldReturnCommand() {
        when(commandService.getCommandById(testId)).thenReturn(testCommand);

        ResponseEntity<Command> response = commandController.getCommandById(testId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(testCommand, response.getBody());
    }

    @Test
    void getCommandById_WhenCommandDoesNotExist_ShouldReturnNotFound() {
        when(commandService.getCommandById(testId)).thenThrow(new RuntimeException("Command not found"));

        ResponseEntity<Command> response = commandController.getCommandById(testId);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void createCommand_ShouldReturnCreatedCommand() {
        when(commandService.createCommand(testCommand)).thenReturn(testCommand);

        Command createdCommand = commandController.createCommand(testCommand);

        assertEquals(testCommand, createdCommand);
        verify(commandService).createCommand(testCommand);
    }

    @Test
    void updateCommand_ShouldReturnUpdatedCommand() {
        when(commandService.updateCommand(testCommand)).thenReturn(testCommand);

        Command updatedCommand = commandController.updateCommand(testId, testCommand);

        assertEquals(testId, updatedCommand.getId());
        assertEquals(testCommand, updatedCommand);
        verify(commandService).updateCommand(testCommand);
    }

    @Test
    void deleteCommand_ShouldReturnOkResponse() {
        doNothing().when(commandService).deleteCommand(testId);

        ResponseEntity<Void> response = commandController.deleteCommand(testId);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(commandService).deleteCommand(testId);
    }
} 