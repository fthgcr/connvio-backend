package com.connvio.server.service;

import com.connvio.common.exception.ConnvioException;
import com.connvio.common.exception.ErrorCode;
import com.connvio.server.dto.CreateServerRequest;
import com.connvio.server.dto.ServerResponse;
import com.connvio.server.entity.Server;
import com.connvio.server.repository.ServerRepository;
import com.connvio.user.entity.User;
import com.connvio.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ServerResponse createServer(CreateServerRequest request, String username) {
        if (StringUtils.isBlank(request.getName())) {
            throw new ConnvioException(ErrorCode.SERVER_NAME_REQUIRED);
        }

        // Aynı isimde sunucu var mı kontrol et
        if (serverRepository.existsByName(request.getName())) {
            throw new ConnvioException(ErrorCode.SERVER_ALREADY_EXISTS);
        }

        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ConnvioException(ErrorCode.USER_NOT_FOUND));

        Server server = new Server();
        server.setName(request.getName().trim());
        server.setDescription(request.getDescription());
        server.setOwner(owner);
        server.getMembers().add(owner);

        server = serverRepository.save(server);
        return convertToResponse(server);
    }

    public List<ServerResponse> getUserServers(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ConnvioException(ErrorCode.USER_NOT_FOUND));

        return serverRepository.findByMembersContaining(user).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ServerResponse convertToResponse(Server server) {
        ServerResponse response = new ServerResponse();
        response.setId(server.getId());
        response.setName(server.getName());
        response.setDescription(server.getDescription());
        response.setOwnerUsername(server.getOwner().getUsername());
        response.setMemberCount(server.getMembers().size());
        return response;
    }

    public void deleteServer(Long serverId) {
        if (!serverRepository.existsById(serverId)) {
            throw new ConnvioException(ErrorCode.SERVER_NOT_FOUND);
        }
        serverRepository.deleteById(serverId);
    }
} 