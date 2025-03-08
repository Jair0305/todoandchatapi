package jair.pruebatecnica.application.service;

import jair.pruebatecnica.domain.model.ChatMessage;
import jair.pruebatecnica.domain.model.MessageType;
import jair.pruebatecnica.domain.ports.spi.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private ChatServiceImpl chatService;

    private ChatMessage chatMessage;

    @BeforeEach
    void setUp() {
        chatMessage = ChatMessage.builder()
                .id("1")
                .content("Test message")
                .sender("testUser")
                .type(MessageType.CHAT)
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    @Test
    void saveMessage_WithoutIdAndTimestamp_ShouldGenerateThemAndSave() {
        ChatMessage inputMessage = ChatMessage.builder()
                .content("Test message")
                .sender("testUser")
                .type(MessageType.CHAT)
                .build();
        
        when(chatRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        ChatMessage result = chatService.saveMessage(inputMessage);

        assertNotNull(result);
        assertEquals(chatMessage.getId(), result.getId());
        verify(chatRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void saveMessage_WithIdAndTimestamp_ShouldSaveAsIs() {
        when(chatRepository.save(chatMessage)).thenReturn(chatMessage);

        ChatMessage result = chatService.saveMessage(chatMessage);

        assertNotNull(result);
        assertEquals(chatMessage.getId(), result.getId());
        assertEquals(chatMessage.getTimestamp(), result.getTimestamp());
        verify(chatRepository, times(1)).save(chatMessage);
    }

    @Test
    void getAllMessages_ShouldReturnAllMessages() {
        List<ChatMessage> messages = Arrays.asList(
                chatMessage,
                ChatMessage.builder().id("2").content("Second message").build()
        );

        when(chatRepository.findAll()).thenReturn(messages);

        List<ChatMessage> result = chatService.getAllMessages();

        assertEquals(2, result.size());
        verify(chatRepository, times(1)).findAll();
    }

    @Test
    void getMessagesBySender_ShouldReturnFilteredMessages() {
        List<ChatMessage> messages = Arrays.asList(chatMessage);

        when(chatRepository.findBySender("testUser")).thenReturn(messages);

        List<ChatMessage> result = chatService.getMessagesBySender("testUser");

        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getSender());
        verify(chatRepository, times(1)).findBySender("testUser");
    }

    @Test
    void notifyUsers_ShouldSaveAndSendMessage() {
        when(chatRepository.save(any(ChatMessage.class))).thenReturn(chatMessage);

        chatService.notifyUsers(chatMessage);

        verify(chatRepository, times(1)).save(any(ChatMessage.class));
        verify(messagingTemplate, times(1)).convertAndSend(eq("/topic/public"), any(ChatMessage.class));
    }
} 