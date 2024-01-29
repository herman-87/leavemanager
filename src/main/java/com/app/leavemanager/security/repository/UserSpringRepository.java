package com.app.leavemanager.security.repository;

import com.app.leavemanager.security.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSpringRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
