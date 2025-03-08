package jair.pruebatecnica.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jair.pruebatecnica.domain.model.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity {
    
    @Id
    private String id;
    
    @Column(length = 1000)
    private String content;
    
    @Column(nullable = false)
    private String sender;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageType type;
    
    @Column(nullable = false)
    private String timestamp;
} 