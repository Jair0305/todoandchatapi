package jair.pruebatecnica.application.dto;

import jair.pruebatecnica.domain.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO para transferir datos de mensajes de chat entre capas

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private String id;
    private String content;
    private String sender;
    private MessageType type;
    private String timestamp;
} 