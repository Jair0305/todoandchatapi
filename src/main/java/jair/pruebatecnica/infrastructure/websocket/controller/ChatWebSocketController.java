package jair.pruebatecnica.infrastructure.websocket.controller;

import jair.pruebatecnica.application.dto.ChatMessageDto;
import jair.pruebatecnica.domain.model.ChatMessage;
import jair.pruebatecnica.domain.model.MessageType;
import jair.pruebatecnica.domain.ports.api.ChatService;
import jair.pruebatecnica.infrastructure.web.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatWebSocketController {

    private final ChatService chatService;
    private final ChatMessageMapper chatMessageMapper;

    //Maneja los mensajes enviados por los usuarios
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto messageDto) {
        log.info("Received message: {}", messageDto);
        
        ChatMessage chatMessage = chatMessageMapper.toDomain(messageDto);
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        
        return chatMessageMapper.toDto(savedMessage);
    }

    //Maneja las conexiones de nuevos usuarios
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageDto addUser(@Payload ChatMessageDto messageDto, 
                                SimpMessageHeaderAccessor headerAccessor) {
        log.info("User joined: {}", messageDto.getSender());
        
        // Guardamos el nombre de usuario en la sesión WebSocket
        headerAccessor.getSessionAttributes().put("username", messageDto.getSender());
        
        // Convertimos y guardamos el mensaje
        ChatMessage chatMessage = chatMessageMapper.toDomain(messageDto);
        
        // Nos aseguramos de que sea un mensaje de tipo JOIN
        chatMessage.setType(MessageType.JOIN);
        
        // Guardamos el mensaje
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);
        
        return chatMessageMapper.toDto(savedMessage);
    }
} 