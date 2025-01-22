package com.streamingbot.discordservice.services.impl;

import com.streamingbot.discordservice.models.Command;
import com.streamingbot.discordservice.repositories.CommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandServiceImplTest {

    @Mock
    private CommandRepository commandRepository;

    @Mock
    private UUIDGeneratorImpl uuidGenerator;

    @InjectMocks
    private CommandServiceImpl commandService;

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
    void getAllCommands_ShouldReturnAllCommands() {
        List<Command> expectedCommands = Arrays.asList(testCommand);
        when(commandRepository.findAll()).thenReturn(expectedCommands);

        List<Command> actualCommands = commandService.getAllCommands();

        assertEquals(expectedCommands, actualCommands);
        verify(commandRepository).findAll();
    }

    @Test
    void getCommandById_WhenCommandExists_ShouldReturnCommand() {
        when(commandRepository.findById(testId)).thenReturn(Optional.of(testCommand));

        Command actualCommand = commandService.getCommandById(testId);

        assertEquals(testCommand, actualCommand);
    }

    @Test
    void getCommandById_WhenCommandDoesNotExist_ShouldThrowException() {
        when(commandRepository.findById(testId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commandService.getCommandById(testId));
    }

    @Test
    void createCommand_WithNullId_ShouldGenerateIdAndSave() {
        Command newCommand = new Command();
        when(uuidGenerator.Generate()).thenReturn(testId);
        when(commandRepository.save(any(Command.class))).thenReturn(testCommand);

        Command createdCommand = commandService.createCommand(newCommand);

        assertEquals(testId, createdCommand.getId());
        verify(commandRepository).save(newCommand);
    }

    @Test
    void updateCommand_WhenCommandExists_ShouldUpdate() {
        when(commandRepository.existsById(testId)).thenReturn(true);
        when(commandRepository.save(testCommand)).thenReturn(testCommand);

        Command updatedCommand = commandService.updateCommand(testCommand);

        assertEquals(testCommand, updatedCommand);
        verify(commandRepository).save(testCommand);
    }

    @Test
    void updateCommand_WhenCommandDoesNotExist_ShouldThrowException() {
        when(commandRepository.existsById(testId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> commandService.updateCommand(testCommand));
    }

    @Test
    void deleteCommand_WhenCommandExists_ShouldDelete() {
        when(commandRepository.existsById(testId)).thenReturn(true);
        doNothing().when(commandRepository).deleteById(testId);

        assertDoesNotThrow(() -> commandService.deleteCommand(testId));
        verify(commandRepository).deleteById(testId);
    }

    @Test
    void deleteCommand_WhenCommandDoesNotExist_ShouldThrowException() {
        when(commandRepository.existsById(testId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> commandService.deleteCommand(testId));
    }
} 