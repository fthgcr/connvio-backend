package com.connvio.server.dto;

import com.connvio.server.entity.ChannelType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChannelResponse {
    private Long id;
    private String name;
    private ChannelType type;
    private Long serverId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 