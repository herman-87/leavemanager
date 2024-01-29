package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.Employee;
import com.app.leavemanager.repository.spring.EmployeeSpringRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultEmployeeRepository implements EmployeeRepository {

    private final EmployeeSpringRepository employeeSpringRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeSpringRepository.saveAndFlush(employee);
    }
}
