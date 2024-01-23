package com.app.leavemanager.DAO;

import com.app.leavemanager.domain.Employee;
import com.app.leavemanager.repository.EmployeeSpringRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultEmployeeDAO implements EmployeeDAO {

    private final EmployeeSpringRepository employeeSpringRepository;

    @Override
    public Employee save(Employee employee) {
        return employeeSpringRepository.saveAndFlush(employee);
    }
}
