package jair.pruebatecnica.infrastructure.persistence.repository;

import jair.pruebatecnica.infrastructure.persistence.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JpaChatMessageRepository extends JpaRepository<ChatMessageEntity, String> {
    
    List<ChatMessageEntity> findBySender(String sender);
} 