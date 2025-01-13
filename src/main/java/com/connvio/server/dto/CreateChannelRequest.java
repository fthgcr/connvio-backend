package com.connvio.server.dto;

import com.connvio.server.entity.ChannelType;
import lombok.Data;

@Data
public class CreateChannelRequest {
    private String name;
    private ChannelType type;
} 