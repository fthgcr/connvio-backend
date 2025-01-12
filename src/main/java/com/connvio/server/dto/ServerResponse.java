package com.connvio.server.dto;

import lombok.Data;

@Data
public class ServerResponse {
    private Long id;
    private String name;
    private String description;
    private String ownerUsername;
    private int memberCount;
} 