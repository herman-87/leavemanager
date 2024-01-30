package com.app.leavemanager.api;

import com.app.leavemanager.dto.EmployeeDTO;
import com.app.leavemanager.dto.RegistrationEmployeeResponseDTO;
import com.app.leavemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminEmployeeResources {

    private final EmployeeService employeeService;

    @PostMapping("/registration/super-admin")
    public ResponseEntity<RegistrationEmployeeResponseDTO> createSuperAdmin(@RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createSuperAdmin(employeeDTO));
    }

    @PostMapping("/registration/admin")
    public ResponseEntity<Long> createAdmin(@RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createAdmin(employeeDTO));
    }

    @PostMapping("/registration/employee")
    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDTO employeeDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(employeeDTO));
    }
}
