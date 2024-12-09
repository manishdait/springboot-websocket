package com.example.websocket_demo.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.websocket_demo.model.Message;
import com.example.websocket_demo.model.MessageType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class WebSocketEventListener {
  private final SimpMessageSendingOperations messageTemplate;

  @EventListener
  public void sessionDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = (String) accessor.getSessionAttributes().get("username");
    if (username != null) {
      Message message = Message.builder()
        .type(MessageType.LEAVE)
        .sender(username)
        .build();
        
      messageTemplate.convertAndSend(message);
    }
  }
}
