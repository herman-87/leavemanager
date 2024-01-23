package com.app.leavemanager.controller;

import com.app.leavemanager.DTO.EmployeeDTO;
import com.app.leavemanager.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Integer> createEmployee(@RequestBody(required = true) EmployeeDTO employeeDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeDTO));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.getAllEmployee());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("employeeId") Integer employeeId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<Void> updateEmployee(@PathVariable("employeeId") Integer employeeId,
                                               @RequestBody(required = true) EmployeeDTO employeeDTO) {

        employeeService.updateEmployee(employeeId, employeeDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") Integer employeeId) {

        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
