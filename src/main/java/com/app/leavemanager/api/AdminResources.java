package com.app.leavemanager.api;

import com.app.leavemanager.service.EmployeeService;
import com.leavemanager.openapi.api.AdminApi;
import com.leavemanager.openapi.model.EmployeeDTO;
import com.leavemanager.openapi.model.RegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminResources implements AdminApi {

    private final EmployeeService employeeService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

//    @PostMapping("/registration")
//    public ResponseEntity<RegistrationEmployeeResponseDTO> createSuperAdmin(@RequestBody EmployeeDTO employeeDTO) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(employeeService.createSuperAdmin(employeeDTO));
//    }

//    @PostMapping("/add")
//    public ResponseEntity<Long> createAdmin(@RequestBody EmployeeDTO employeeDTO) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(employeeService.createAdmin(employeeDTO, getCurrentUsername()));
//    }

//    @PostMapping("/employee/add")
//    public ResponseEntity<Long> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(employeeService.createEmployee(employeeDTO, getCurrentUsername()));
//    }

//    @GetMapping("/employee/all")
//    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(employeeService.getAllEmployees());
//    }

    @Override
    public ResponseEntity<Long> _createAdmin(RegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Long> _createEmployee(RegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Long> _createSuperAdmin(RegistrationDTO registrationDTO) {
        return null;
    }

    @Override
    public ResponseEntity<List<EmployeeDTO>> _getAllEmployees() {
        return null;
    }
}
