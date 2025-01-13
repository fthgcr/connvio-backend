package com.connvio.server.service;

import com.connvio.common.exception.ConnvioException;
import com.connvio.common.exception.ErrorCode;
import com.connvio.server.dto.ChannelResponse;
import com.connvio.server.dto.CreateChannelRequest;
import com.connvio.server.entity.Channel;
import com.connvio.server.entity.Server;
import com.connvio.server.repository.ChannelRepository;
import com.connvio.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;

    @Transactional
    public ChannelResponse createChannel(Long serverId, CreateChannelRequest request, String username) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new ConnvioException(ErrorCode.SERVER_NOT_FOUND));

        if (!server.getOwner().getUsername().equals(username)) {
            throw new ConnvioException(ErrorCode.NOT_SERVER_OWNER);
        }

        if (channelRepository.existsByServerIdAndName(serverId, request.getName())) {
            throw new ConnvioException(ErrorCode.CHANNEL_ALREADY_EXISTS);
        }

        Channel channel = new Channel();
        channel.setName(request.getName());
        channel.setType(request.getType());
        channel.setServer(server);

        channel = channelRepository.save(channel);
        return convertToResponse(channel);
    }

    public List<ChannelResponse> getServerChannels(Long serverId) {
        return channelRepository.findByServerId(serverId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ChannelResponse convertToResponse(Channel channel) {
        ChannelResponse response = new ChannelResponse();
        response.setId(channel.getId());
        response.setName(channel.getName());
        response.setType(channel.getType());
        response.setServerId(channel.getServer().getId());
        response.setCreatedAt(channel.getCreatedAt());
        response.setUpdatedAt(channel.getUpdatedAt());
        return response;
    }
} 