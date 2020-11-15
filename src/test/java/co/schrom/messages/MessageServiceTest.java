package co.schrom.messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    MessageService messageService;

    @Mock
    MessageInterface mockedA, mockedB, mockedC, mockedD;

    @BeforeEach
    void beforeEach() {
        messageService = MessageService.getInstance();
        if (messageService != null) {
            messageService.messages = new ArrayList<>(Arrays.asList(mockedA, mockedB, mockedC));
        }
    }

    @Test
    @DisplayName("The MessageService class should implement the singleton pattern.")
    void testMessageService__singleton() {
        // act
        MessageService messageService2 = MessageService.getInstance();

        // assert
        assertNotNull(messageService);
        assertEquals(messageService, messageService2);
    }

    @Test
    @DisplayName("Get all messages from service")
    void testGetMessages() {
        // act
        List<MessageInterface> expectedMessages = new ArrayList<>(Arrays.asList(mockedA, mockedB, mockedC));
        List<MessageInterface> messages = messageService.getMessages();

        // assert
        assertEquals(expectedMessages, messages);
    }

    @Test
    @DisplayName("Get a specific message")
    void testGetMessage() {
        // arrange
        when(mockedA.getId()).thenReturn(1);

        // act
        MessageInterface message = messageService.getMessage(1);

        // assert
        assertEquals(mockedA, message);
    }

    @Test
    @DisplayName("Add a message to the service")
    void testAddMessages() {
        // act
        List<MessageInterface> expectedMessages = new ArrayList<>(Arrays.asList(mockedA, mockedB, mockedC, mockedD));
        boolean returnValue = messageService.addMessage(mockedD);

        // assert
        assertTrue(returnValue);
        assertEquals(expectedMessages, messageService.getMessages());
    }

    @Test
    @DisplayName("Replace a message to the service")
    void testReplaceMessages() {
        // arrange
        when(mockedA.getId()).thenReturn(1);

        // act
        List<MessageInterface> expectedMessages = new ArrayList<>(Arrays.asList(mockedD, mockedB, mockedC));
        boolean returnValue = messageService.replaceMessage(1, mockedD);

        // assert
        assertTrue(returnValue);
        assertEquals(expectedMessages, messageService.getMessages());
    }

    @Test
    @DisplayName("Remove a message from the service")
    void testRemoveMessage() {
        // arrange
        when(mockedC.getId()).thenReturn(1);

        // act
        List<MessageInterface> expectedMessages = new ArrayList<>(Arrays.asList(mockedA, mockedB));
        boolean returnValue = messageService.removeMessage(1);

        // assert
        assertTrue(returnValue);
        assertEquals(expectedMessages, messageService.getMessages());
    }
}
