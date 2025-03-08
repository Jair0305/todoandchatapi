package jair.pruebatecnica.infrastructure.web.controller;

import jair.pruebatecnica.application.dto.ChatMessageDto;
import jair.pruebatecnica.domain.model.ChatMessage;
import jair.pruebatecnica.domain.ports.api.ChatService;
import jair.pruebatecnica.infrastructure.web.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;
    private final ChatMessageMapper chatMessageMapper;

    //Obtiene todos los mensajes del chat
    @GetMapping("/messages")
    public List<ChatMessageDto> getAllMessages() {
        return chatService.getAllMessages().stream()
                .map(chatMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    //Obtiene mensajes de un remitente específico
    @GetMapping("/messages/sender/{sender}")
    public List<ChatMessageDto> getMessagesBySender(@PathVariable String sender) {
        return chatService.getMessagesBySender(sender).stream()
                .map(chatMessageMapper::toDto)
                .collect(Collectors.toList());
    }

    //Envía un mensaje al chat (usando REST en lugar de WebSocket)
    //Útil para pruebas o cuando no se puede usar WebSocket
    @PostMapping("/messages")
    public ResponseEntity<ChatMessageDto> sendMessage(@RequestBody ChatMessageDto messageDto) {
        ChatMessage chatMessage = chatMessageMapper.toDomain(messageDto);
        chatService.notifyUsers(chatMessage);
        return ResponseEntity.ok(chatMessageMapper.toDto(chatMessage));
    }
}