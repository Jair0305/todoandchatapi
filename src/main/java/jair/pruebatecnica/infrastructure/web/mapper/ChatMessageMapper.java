package jair.pruebatecnica.infrastructure.web.mapper;

import jair.pruebatecnica.application.dto.ChatMessageDto;
import jair.pruebatecnica.domain.model.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    //Convierte un objeto de dominio a DTO
    public ChatMessageDto toDto(ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        
        return ChatMessageDto.builder()
                .id(chatMessage.getId())
                .content(chatMessage.getContent())
                .sender(chatMessage.getSender())
                .type(chatMessage.getType())
                .timestamp(chatMessage.getTimestamp())
                .build();
    }

    //Convierte un DTO a objeto de dominio
    public ChatMessage toDomain(ChatMessageDto dto) {
        if (dto == null) {
            return null;
        }
        
        return ChatMessage.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .sender(dto.getSender())
                .type(dto.getType())
                .timestamp(dto.getTimestamp())
                .build();
    }
} 