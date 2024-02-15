package com.app.leavemanager.api;

import com.app.leavemanager.service.EmployeeService;
import com.leavemanager.openapi.api.EmployeeApi;
import com.leavemanager.openapi.model.EmployeeDTO;
import com.leavemanager.openapi.model.RegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeResources implements EmployeeApi {

    private final EmployeeService employeeService;


//    @DeleteMapping("/{employeeId}")
//    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") Long employeeId) {
//
//        employeeService.deleteEmployee(employeeId);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//    }
//
//    @PutMapping("/validate")
//    public ResponseEntity<Void> validate() {
//        employeeService.validate(getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//    }
//
    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<Void> _deleteEmployeeById(Long employeeId) {
        return null;
    }

    @Override
    public ResponseEntity<EmployeeDTO> _getEmployeeById(Long employeeId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(employeeDTO, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
