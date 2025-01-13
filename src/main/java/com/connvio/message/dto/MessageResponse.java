package com.connvio.message.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long id;
    private String content;
    private Long channelId;
    private String senderUsername;
    private String senderAvatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 