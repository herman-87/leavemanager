package com.app.leavemanager.service;

import com.app.leavemanager.domain.Employee;
import com.app.leavemanager.dto.EmployeeDTO;
import com.app.leavemanager.repository.dao.DefaultEmployeeRepository;
import com.app.leavemanager.repository.spring.EmployeeSpringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeSpringRepository employeeSpringRepository;
    @Value("${api.admin.email}")
    private String adminEmail;
    @Value("${api.admin.password}")
    private String password;

    @Transactional
    public Long createEmployee(EmployeeDTO employeeDTO) {

        return Employee.create(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                new DefaultEmployeeRepository(employeeSpringRepository)
        ).getId();
    }

    @Transactional
    public List<EmployeeDTO> getAllEmployee() {
        return employeeSpringRepository.findAll()
                .stream()
                .map(employee ->
                        EmployeeDTO.builder()
                                .id(employee.getId())
                                .firstname(employee.getFirstname())
                                .lastname(employee.getLastname())
                                .dateOfBirth(employee.getDateOfBirth())
                                .build()
                )
                .toList();
    }

    @Transactional
    public void updateEmployee(Long employeeId, EmployeeDTO employeeDTO) {

        Employee employee = employeeSpringRepository.findById(employeeId).orElseThrow();
        employee.update(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                new DefaultEmployeeRepository(employeeSpringRepository)
        );
    }

    @Transactional
    public void deleteEmployee(Long employeeId) {
        employeeSpringRepository.deleteById(employeeId);
    }

    @Transactional
    public EmployeeDTO getEmployeeById(Long employeeId) {
        return employeeSpringRepository.findById(employeeId)
                .map(employee -> EmployeeDTO.builder()
                        .id(employee.getId())
                        .firstname(employee.getFirstname())
                        .lastname(employee.getLastname())
                        .dateOfBirth(employee.getDateOfBirth())
                        .build())
                .orElseThrow();
    }

    @Transactional
    public Long createSuperAdmin(EmployeeDTO employeeDTO) {
        return Employee.createSuperAdmin(
                adminEmail,
                password,
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                new DefaultEmployeeRepository(employeeSpringRepository)
        ).getId();
    }
}
