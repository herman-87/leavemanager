package com.app.leavemanager.configurations.security.api;

import com.app.leavemanager.configurations.security.model.AuthenticationRequest;
import com.app.leavemanager.configurations.security.service.AuthenticationService;
import com.app.leavemanager.dto.TokenDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("/username")
    public ResponseEntity<Object> getUsername() {
        return ResponseEntity.ok(authenticationService.getUsername());
    }
}
