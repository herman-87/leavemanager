package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Role;

import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);

    boolean existsByRole(Role role);

    Optional<Employee> findByUsername(String name);
}
