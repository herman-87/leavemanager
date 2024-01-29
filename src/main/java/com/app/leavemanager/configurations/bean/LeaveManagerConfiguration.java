package com.app.leavemanager.configurations.bean;

import com.app.leavemanager.repository.dao.DefaultEmployeeRepository;
import com.app.leavemanager.repository.dao.EmployeeRepository;
import com.app.leavemanager.repository.spring.EmployeeSpringRepository;
import com.app.leavemanager.configurations.security.repository.UserSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class LeaveManagerConfiguration {

    private final UserSpringRepository userSpringRepository;
    private final EmployeeSpringRepository employeeSpringRepository;

    @Bean
    public EmployeeRepository employeeRepository() {
        return new DefaultEmployeeRepository(
                employeeSpringRepository,
                userSpringRepository
        );
    }

}
