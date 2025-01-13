package com.connvio.message.service;

import com.connvio.common.exception.ConnvioException;
import com.connvio.common.exception.ErrorCode;
import com.connvio.message.dto.CreateMessageRequest;
import com.connvio.message.dto.MessageResponse;
import com.connvio.message.entity.Message;
import com.connvio.message.repository.MessageRepository;
import com.connvio.server.entity.Channel;
import com.connvio.server.repository.ChannelRepository;
import com.connvio.user.entity.User;
import com.connvio.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageResponse createMessage(Long channelId, CreateMessageRequest request, String username) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ConnvioException(ErrorCode.CHANNEL_NOT_FOUND));

        User sender = userRepository.findByUsername(username)
                .orElseThrow(() -> new ConnvioException(ErrorCode.USER_NOT_FOUND));

        Message message = new Message();
        message.setContent(request.getContent());
        message.setChannel(channel);
        message.setSender(sender);

        message = messageRepository.save(message);
        return convertToResponse(message);
    }

    public Page<MessageResponse> getChannelMessages(Long channelId, Pageable pageable) {
        return messageRepository.findByChannelIdOrderByCreatedAtDesc(channelId, pageable)
                .map(this::convertToResponse);
    }

    private MessageResponse convertToResponse(Message message) {
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setContent(message.getContent());
        response.setChannelId(message.getChannel().getId());
        response.setSenderUsername(message.getSender().getUsername());
        response.setSenderAvatarUrl(message.getSender().getAvatarUrl());
        response.setCreatedAt(message.getCreatedAt());
        response.setUpdatedAt(message.getUpdatedAt());
        return response;
    }
} 