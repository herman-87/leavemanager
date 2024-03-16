package com.app.leavemanager.configurations.security.api;

import com.app.leavemanager.configurations.security.service.AuthenticationService;
import com.leavemanager.openapi.api.AuthenticationApi;
import com.leavemanager.openapi.model.AuthenticationRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;

    @GetMapping("/username")
    public ResponseEntity<Object> getUsername() {
        return ResponseEntity.ok(authenticationService.getUsername());
    }

    @Override
    public ResponseEntity<com.leavemanager.openapi.model.TokenDTO> _authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }
}
