package com.connvio.server.controller;

import com.connvio.server.dto.ServerInviteResponse;
import com.connvio.server.service.ServerInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servers")
@RequiredArgsConstructor
public class ServerInviteController {
    private final ServerInviteService serverInviteService;

    @PostMapping("/{serverId}/invites")
    public ResponseEntity<ServerInviteResponse> createInvite(
            @PathVariable Long serverId,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(serverInviteService.createInvite(serverId, username, 24)); // 24 saat geçerli
    }

    @PostMapping("/join/{inviteCode}")
    public ResponseEntity<ServerInviteResponse> joinServer(
            @PathVariable String inviteCode,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(serverInviteService.joinServer(inviteCode, username));
    }
} 