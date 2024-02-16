package com.app.leavemanager.api;

import com.app.leavemanager.service.EmployeeService;
import com.leavemanager.openapi.api.AdminApi;
import com.leavemanager.openapi.model.EmployeeDTO;
import com.leavemanager.openapi.model.RegistrationDTO;
import com.leavemanager.openapi.model.RegistrationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminResources implements AdminApi {

    private final EmployeeService employeeService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<Long> _createAdmin(RegistrationDTO registrationDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createAdmin(registrationDTO, getCurrentUsername()));
    }

    @Override
    public ResponseEntity<Long> _createEmployee(RegistrationDTO registrationDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(registrationDTO, getCurrentUsername()));
    }

    @Override
    public ResponseEntity<RegistrationResponseDTO> _createSuperAdmin(RegistrationDTO registrationDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createSuperAdmin(registrationDTO));
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> _getAllEmployees() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.getAllEmployees());
    }
}
