package jair.pruebatecnica.infrastructure.websocket.event;

import jair.pruebatecnica.domain.model.ChatMessage;
import jair.pruebatecnica.domain.model.MessageType;
import jair.pruebatecnica.domain.ports.api.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Listener para eventos de WebSocket
@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final ChatService chatService;

    //Maneja eventos de desconexión WebSocket
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        
        // Recuperamos el nombre de usuario de la sesión
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        
        if (username != null) {
            log.info("User Disconnected: {}", username);
            
            // Creamos un mensaje de salida
            ChatMessage chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .content(username + " ha abandonado el chat!")
                    .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                    .build();
            
            // Notificamos a todos los usuarios sobre la salida
            chatService.notifyUsers(chatMessage);
        }
    }
} 