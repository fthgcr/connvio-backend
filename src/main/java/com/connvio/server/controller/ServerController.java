package com.connvio.server.controller;

import com.connvio.server.dto.CreateServerRequest;
import com.connvio.server.dto.ServerResponse;
import com.connvio.server.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @PostMapping
    public ResponseEntity<ServerResponse> createServer(
            @RequestBody CreateServerRequest request,
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(serverService.createServer(request, username));
    }

    @GetMapping
    public ResponseEntity<List<ServerResponse>> getUserServers(
            @AuthenticationPrincipal String username) {
        return ResponseEntity.ok(serverService.getUserServers(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable Long id) {
        serverService.deleteServer(id);
        return ResponseEntity.noContent().build();
    }
} 