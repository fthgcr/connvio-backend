package com.connvio.message.dto;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private Long channelId;
} 