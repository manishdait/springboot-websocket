package com.example.websocket_demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.example.websocket_demo.model.Message;

@Controller
public class ChatController {
  @MessageMapping("/chat.sendMessage")
  @SendTo("/topic/public")
  public Message sendMessage(@Payload Message message) {
    return message;
  } 

  @MessageMapping("/chat.addUser")
  @SendTo("/topic/public")
  public Message addUser(@Payload Message message, SimpMessageHeaderAccessor accessor) {
    // Add username in websocket session
    accessor.getSessionAttributes().put("username", message.getSender());
    return message;
  }
}
