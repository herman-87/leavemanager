package com.app.leavemanager.service;

import com.app.leavemanager.DAO.DefaultEmployeeDAO;
import com.app.leavemanager.DTO.EmployeeDTO;
import com.app.leavemanager.domain.Employee;
import com.app.leavemanager.repository.EmployeeSpringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeSpringRepository employeeSpringRepository;

    @Transactional
    public Integer createEmployee(EmployeeDTO employeeDTO) {

        return Employee.create(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                new DefaultEmployeeDAO(employeeSpringRepository)
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
    public void updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {

        Employee employee = employeeSpringRepository.findById(employeeId).orElseThrow();
        employee.update(
                employeeDTO.getFirstname(),
                employeeDTO.getLastname(),
                employeeDTO.getDateOfBirth(),
                new DefaultEmployeeDAO(employeeSpringRepository)
        );
    }

    @Transactional
    public void deleteEmployee(Integer employeeId) {
        employeeSpringRepository.deleteById(employeeId);
    }

    @Transactional
    public EmployeeDTO getEmployeeById(Integer employeeId) {
        return employeeSpringRepository.findById(employeeId)
                .map(employee -> EmployeeDTO.builder()
                        .id(employee.getId())
                        .firstname(employee.getFirstname())
                        .lastname(employee.getLastname())
                        .dateOfBirth(employee.getDateOfBirth())
                        .build())
                .orElseThrow();
    }
}
