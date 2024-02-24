package com.app.leavemanager.configurations.security.service;

import com.app.leavemanager.configurations.security.model.token.Token;
import com.app.leavemanager.configurations.security.model.token.TokenType;
import com.app.leavemanager.configurations.security.repository.TokenSpringRepository;
import com.app.leavemanager.configurations.security.repository.UserSpringRepository;
import com.app.leavemanager.domain.employee.user.User;
import com.leavemanager.openapi.model.AuthenticationRequestDTO;
import com.leavemanager.openapi.model.TokenDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserSpringRepository userSpringRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenSpringRepository tokenSpringRepository;

    private void revokeAllUserTokens(User user) {

        List<Token> allValidTokensByUser = tokenSpringRepository.findAllValidTokensByUser(user.getId());
        if (!allValidTokensByUser.isEmpty()) {
            tokenSpringRepository.deleteAll(allValidTokensByUser);
        }
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
    public TokenDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.getEmail(),
                        authenticationRequestDTO.getPassword()
                )
        );

        User user = userSpringRepository.findByEmail(authenticationRequestDTO.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(Map.of("scopes", user.getRole()), user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new TokenDTO().value(jwtToken);
    }

    public Object getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
