package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.Employee;

public interface EmployeeRepository {
    Employee save(Employee employee);
}
