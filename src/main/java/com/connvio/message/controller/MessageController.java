package com.connvio.message.controller;

import com.connvio.message.dto.CreateMessageRequest;
import com.connvio.message.dto.MessageResponse;
import com.connvio.message.dto.ChatMessage;
import com.connvio.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/channels/{channelId}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageResponse> createMessage(
            @PathVariable("channelId") Long channelId,
            @RequestBody CreateMessageRequest request,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(messageService.createMessage(channelId, request, username));
    }

    @GetMapping
    public ResponseEntity<Page<MessageResponse>> getChannelMessages(
            @PathVariable("channelId") Long channelId,
            Pageable pageable) {
        return ResponseEntity.ok(messageService.getChannelMessages(channelId, pageable));
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/channel/{channelId}")
    public MessageResponse sendMessage(@Payload ChatMessage chatMessage) {
        return messageService.createMessage(
            chatMessage.getChannelId(), 
            new CreateMessageRequest(chatMessage.getContent()), 
            chatMessage.getSender()
        );
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/channel/{channelId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                             SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("channelId", chatMessage.getChannelId());
        return chatMessage;
    }
} 