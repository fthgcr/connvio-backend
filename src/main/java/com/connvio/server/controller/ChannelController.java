package com.connvio.server.controller;

import com.connvio.server.dto.ChannelResponse;
import com.connvio.server.dto.CreateChannelRequest;
import com.connvio.server.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers/{serverId}/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelResponse> createChannel(
            @PathVariable("serverId") Long serverId,
            @RequestBody CreateChannelRequest request,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(channelService.createChannel(serverId, request, username));
    }

    @GetMapping
    public ResponseEntity<List<ChannelResponse>> getServerChannels(
            @PathVariable("serverId") Long serverId) {
        return ResponseEntity.ok(channelService.getServerChannels(serverId));
    }
} 