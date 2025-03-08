package jair.pruebatecnica.domain.ports.spi;

import jair.pruebatecnica.domain.model.ChatMessage;

import java.util.List;
import java.util.Optional;


public interface ChatRepository {
    
    //Guarda un mensaje de chat
    ChatMessage save(ChatMessage chatMessage);
    
    //Busca un mensaje por su ID
    Optional<ChatMessage> findById(String id);
    
    //Obtiene todos los mensajes
    List<ChatMessage> findAll();
    
    //Obtiene mensajes de un remitente específico
    List<ChatMessage> findBySender(String sender);
} 