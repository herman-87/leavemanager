package com.app.leavemanager.configurations.security.service;

import com.app.leavemanager.configurations.security.model.token.Token;
import com.app.leavemanager.configurations.security.repository.TokenSpringRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenSpringRepository tokenSpringRepository;

    @Override
    @Transactional
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        final String autHeader = request.getHeader("Authorization");
        final String jwt;

        if (Objects.isNull(autHeader) || !autHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = autHeader.substring(7);
        Token storedToken = tokenSpringRepository.findByToken(jwt).orElse(null);

        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenSpringRepository.save(storedToken);
        }
    }
}
