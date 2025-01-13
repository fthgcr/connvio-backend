package com.connvio.server.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ServerInviteResponse {
    private String code;
    private Long serverId;
    private String serverName;
    private String createdBy;
    private LocalDateTime expiresAt;
} 