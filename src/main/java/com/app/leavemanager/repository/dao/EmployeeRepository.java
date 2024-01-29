package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Role;

public interface EmployeeRepository {
    Employee save(Employee employee);

    boolean isUserExistsByRole(Role role);
}
