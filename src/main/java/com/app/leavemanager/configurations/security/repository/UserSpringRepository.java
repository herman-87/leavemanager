package com.app.leavemanager.configurations.security.repository;

import com.app.leavemanager.domain.employee.user.Scope;
import com.app.leavemanager.domain.employee.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSpringRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    boolean existsByRole(Scope role);
}
