package com.connvio.server.repository;

import com.connvio.server.entity.ServerInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ServerInviteRepository extends JpaRepository<ServerInvite, Long> {
    Optional<ServerInvite> findByCodeAndIsActiveAndExpiresAtAfter(
        String code, 
        boolean isActive, 
        LocalDateTime now
    );
} 