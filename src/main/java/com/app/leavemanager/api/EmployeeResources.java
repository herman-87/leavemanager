package com.app.leavemanager.api;

import com.app.leavemanager.service.EmployeeService;
import com.leavemanager.openapi.api.EmployeeApi;
import com.leavemanager.openapi.model.EmployeeDTO;
import com.leavemanager.openapi.model.PassWordDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeResources implements EmployeeApi {

    private final EmployeeService employeeService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<Void> _deleteEmployeeById(Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<EmployeeDTO> _getEmployeeById(Long employeeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.getEmployeeById(employeeId));
    }

    @Override
    public ResponseEntity<PassWordDTO> _getPassword() {
        return null;
    }

    @Override
    public ResponseEntity<Void> _updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employeeDTO, employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _updatePassword(PassWordDTO passWordDTO) {
        return null;
    }
}
