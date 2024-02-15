package com.app.leavemanager.domain.employee;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Scope;

import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);

    boolean existsByRole(Scope role);

    Optional<Employee> findByUsername(String name);
}
