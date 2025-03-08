package jair.pruebatecnica.domain.ports.api;

import jair.pruebatecnica.domain.model.ChatMessage;

import java.util.List;

public interface ChatService {
    
    //Guarda un nuevo mensaje de chat
    ChatMessage saveMessage(ChatMessage chatMessage);
    //Obtiene todos los mensajes del chat
    List<ChatMessage> getAllMessages();
    //Obtiene mensajes de un remitente específico
    List<ChatMessage> getMessagesBySender(String sender);
    //Notifica a todos los usuarios conectados sobre un nuevo mensaje
    void notifyUsers(ChatMessage chatMessage);
} 