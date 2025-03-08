package jair.pruebatecnica.infrastructure.persistence.adapter;

import jair.pruebatecnica.domain.model.ChatMessage;
import jair.pruebatecnica.domain.ports.spi.ChatRepository;
import jair.pruebatecnica.infrastructure.persistence.entity.ChatMessageEntity;
import jair.pruebatecnica.infrastructure.persistence.repository.JpaChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Adaptador que implementa el puerto secundario ChatRepository
//conectando el dominio con la infraestructura de persistencia
@Component
@RequiredArgsConstructor
public class ChatRepositoryAdapter implements ChatRepository {

    private final JpaChatMessageRepository jpaChatMessageRepository;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        ChatMessageEntity entity = mapToEntity(chatMessage);
        ChatMessageEntity savedEntity = jpaChatMessageRepository.save(entity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<ChatMessage> findById(String id) {
        return jpaChatMessageRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public List<ChatMessage> findAll() {
        return jpaChatMessageRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessage> findBySender(String sender) {
        return jpaChatMessageRepository.findBySender(sender).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    //Mapea una entidad JPA a un objeto de dominio
    private ChatMessage mapToDomain(ChatMessageEntity entity) {
        return ChatMessage.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .sender(entity.getSender())
                .type(entity.getType())
                .timestamp(entity.getTimestamp())
                .build();
    }

    //Mapea un objeto de dominio a una entidad JPA
    private ChatMessageEntity mapToEntity(ChatMessage chatMessage) {
        return ChatMessageEntity.builder()
                .id(chatMessage.getId())
                .content(chatMessage.getContent())
                .sender(chatMessage.getSender())
                .type(chatMessage.getType())
                .timestamp(chatMessage.getTimestamp())
                .build();
    }
} 