package com.connvio.server.service;

import com.connvio.server.dto.ServerInviteResponse;
import com.connvio.server.entity.Server;
import com.connvio.server.entity.ServerInvite;
import com.connvio.user.entity.User;
import com.connvio.server.repository.ServerInviteRepository;
import com.connvio.server.repository.ServerRepository;
import com.connvio.user.repository.UserRepository;
import com.connvio.common.exception.ConnvioException;
import com.connvio.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServerInviteService {
    private final ServerInviteRepository serverInviteRepository;
    private final ServerRepository serverRepository;
    private final UserRepository userRepository;

    @Transactional
    public ServerInviteResponse createInvite(Long serverId, String username, int expirationHours) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ConnvioException(ErrorCode.SERVER_NOT_FOUND));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ConnvioException(ErrorCode.USER_NOT_FOUND));

        // Kullanıcının sunucu üyesi olup olmadığını kontrol et
        if (!server.getMembers().contains(user)) {
            throw new ConnvioException(ErrorCode.NOT_SERVER_MEMBER);
        }

        ServerInvite invite = new ServerInvite();
        invite.setCode(generateUniqueCode());
        invite.setServer(server);
        invite.setCreatedBy(user);
        invite.setCreatedAt(LocalDateTime.now());
        invite.setExpiresAt(LocalDateTime.now().plusHours(expirationHours));

        invite = serverInviteRepository.save(invite);

        return convertToResponse(invite);
    }

    @Transactional
    public ServerInviteResponse joinServer(String inviteCode, String username) {
        ServerInvite invite = serverInviteRepository
                .findByCodeAndIsActiveAndExpiresAtAfter(inviteCode, true, LocalDateTime.now())
                .orElseThrow(() -> new ConnvioException(ErrorCode.INVALID_INVITE_CODE));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ConnvioException(ErrorCode.USER_NOT_FOUND));

        Server server = invite.getServer();

        // Kullanıcı zaten sunucu üyesi mi kontrol et
        if (server.getMembers().contains(user)) {
            throw new ConnvioException(ErrorCode.ALREADY_SERVER_MEMBER);
        }

        server.getMembers().add(user);
        serverRepository.save(server);

        return convertToResponse(invite);
    }

    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private ServerInviteResponse convertToResponse(ServerInvite invite) {
        ServerInviteResponse response = new ServerInviteResponse();
        response.setCode(invite.getCode());
        response.setServerId(invite.getServer().getId());
        response.setServerName(invite.getServer().getName());
        response.setCreatedBy(invite.getCreatedBy().getUsername());
        response.setExpiresAt(invite.getExpiresAt());
        return response;
    }
} 