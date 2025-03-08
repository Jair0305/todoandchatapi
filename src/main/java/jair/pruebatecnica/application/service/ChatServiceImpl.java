package jair.pruebatecnica.application.service;

import jair.pruebatecnica.domain.model.ChatMessage;
import jair.pruebatecnica.domain.ports.api.ChatService;
import jair.pruebatecnica.domain.ports.spi.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
//Implementación de la lógica de negocio para mensajes de chat
 
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Override
    public ChatMessage saveMessage(ChatMessage chatMessage) {
        // Generamos un ID único y timestamp si no existen
        if (chatMessage.getId() == null) {
            chatMessage.setId(UUID.randomUUID().toString());
        }
        
        if (chatMessage.getTimestamp() == null) {
            chatMessage.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        
        return chatRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> getAllMessages() {
        return chatRepository.findAll();
    }

    @Override
    public List<ChatMessage> getMessagesBySender(String sender) {
        return chatRepository.findBySender(sender);
    }

    @Override
    public void notifyUsers(ChatMessage chatMessage) {
        // Guardamos el mensaje primero
        ChatMessage savedMessage = saveMessage(chatMessage);
        
        // Enviamos el mensaje a todos los clientes suscritos
        messagingTemplate.convertAndSend("/topic/public", savedMessage);
    }
} 