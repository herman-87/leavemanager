package com.app.leavemanager.api;

import com.app.leavemanager.dto.EmployeeDTO;
import com.app.leavemanager.dto.RegistrationEmployeeResponseDTO;
import com.app.leavemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminResources {

    private final EmployeeService employeeService;

    @PostMapping("/registration")
    public ResponseEntity<RegistrationEmployeeResponseDTO> createSuperAdmin(@RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createSuperAdmin(employeeDTO));
    }

    @PostMapping("/add")
    public ResponseEntity<Long> createAdmin(@RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createAdmin(employeeDTO, getCurrentUsername()));
    }

    @PostMapping("/employee/add")
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employeeDTO, getCurrentUsername()));
    }

    @GetMapping("/employee/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.getAllEmployees());
    }

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
