package com.app.leavemanager.configurations.security.service;

import com.app.leavemanager.configurations.security.model.token.Token;
import com.app.leavemanager.configurations.security.model.token.TokenType;
import com.app.leavemanager.configurations.security.model.AuthenticationRequest;
import com.app.leavemanager.dto.RegistrationEmployeeResponseDTO;
import com.app.leavemanager.domain.employee.user.Role;
import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.configurations.security.repository.TokenSpringRepository;
import com.app.leavemanager.configurations.security.repository.UserSpringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserSpringRepository userSpringRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenSpringRepository tokenSpringRepository;

    private void revokeAllUserTokens(User user) {

        List<Token> allValidTokensByUser = tokenSpringRepository.findAllValidTokensByUser(user.getId());
        if (allValidTokensByUser.isEmpty()) {
            return;
        }
        allValidTokensByUser.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenSpringRepository.saveAll(allValidTokensByUser);
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .user(savedUser)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenSpringRepository.save(token);
    }

    @Transactional
    public RegistrationEmployeeResponseDTO authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        User user = userSpringRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return RegistrationEmployeeResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public Object getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
