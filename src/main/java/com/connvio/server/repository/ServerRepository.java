package com.connvio.server.repository;

import com.connvio.server.entity.Server;
import com.connvio.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    boolean existsByName(String name);
    List<Server> findByMembersContaining(User user);
} 