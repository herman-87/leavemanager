package com.app.leavemanager.service;

import com.app.leavemanager.domain.Employee;
import com.app.leavemanager.dto.EmployeeDTO;
import com.app.leavemanager.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional
    public Integer createEmployee(EmployeeDTO employeeDTO) {

        Employee employee = Employee.builder()
                .firstname(employeeDTO.getFirstname())
                .lastname(employeeDTO.getLastname())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .build();

        log.info("Employee is initialize");
        return employeeRepository.saveAndFlush(employee).getId();
    }

    @Transactional
    public List<EmployeeDTO> getAllEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(employee ->
                        EmployeeDTO.builder()
                                .firstname(employee.getFirstname())
                                .lastname(employee.getLastname())
                                .dateOfBirth(employee.getDateOfBirth())
                                .build()
                )
                .toList();
    }

    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeDTO.getId())
                .orElseThrow();
        employee.update(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth()
        );
    }

    @Transactional
    public void deleteEmployee(Integer employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
