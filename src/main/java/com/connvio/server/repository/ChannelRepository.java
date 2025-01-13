package com.connvio.server.repository;

import com.connvio.server.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    List<Channel> findByServerId(Long serverId);
    boolean existsByServerIdAndName(Long serverId, String name);
} 