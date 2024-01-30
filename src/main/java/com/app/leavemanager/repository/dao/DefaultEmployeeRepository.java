package com.app.leavemanager.repository.dao;

import com.app.leavemanager.configurations.security.repository.UserSpringRepository;
import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Role;
import com.app.leavemanager.repository.spring.EmployeeSpringRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DefaultEmployeeRepository implements EmployeeRepository {

    private final EmployeeSpringRepository employeeSpringRepository;
    private final UserSpringRepository userSpringRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeSpringRepository.saveAndFlush(employee);
    }

    @Override
    public boolean existsByRole(Role role) {
        return employeeSpringRepository.existsByUserRole(role);
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        return employeeSpringRepository.findByUserEmail(username);
    }
}
