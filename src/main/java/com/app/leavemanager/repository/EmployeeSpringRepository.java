package com.app.leavemanager.repository;

import com.app.leavemanager.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeSpringRepository extends JpaRepository<Employee, Integer> {
}