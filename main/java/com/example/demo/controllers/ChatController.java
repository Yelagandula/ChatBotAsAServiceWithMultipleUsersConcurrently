package com.example.demo.controllers;

import com.example.demo.models.ChatMessage;
import com.example.demo.models.ChatSession;
import com.example.demo.repositories.ChatSessionRepository;
import com.example.demo.services.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "https://locathost:3000")
public class ChatController {

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // For broadcasting

    @PostMapping("/message")
    public ChatMessage chatWithBot(@RequestBody Map<String, String> payload) {
    String userId = payload.get("userId");
    String userMsg = payload.get("message");

    String aiReply = openAIService.generateResponse(userMsg);

    // Save to DB
    ChatSession session = new ChatSession();
    session.setUserId(userId);
    session.setMessage(userMsg);
    session.setBotResponse(aiReply);
    session.setTimestamp(LocalDateTime.now());
    chatSessionRepository.save(session);

    // Build a ChatMessage to broadcast
    ChatMessage broadcast = new ChatMessage();
    broadcast.setUserId(userId);
    broadcast.setMessage("User: " + userMsg + "\nBot: " + aiReply);
    broadcast.setTimestamp(
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    );

    // (Optional) Broadcast via STOMP
    messagingTemplate.convertAndSend("/topic/messages", broadcast);

    return broadcast;
}
}