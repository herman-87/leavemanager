package com.app.leavemanager.configurations.bean;

import com.app.leavemanager.service.EmployeeService;
import com.leavemanager.openapi.model.RegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AppStartupRunner implements CommandLineRunner {
    private final EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {
        employeeService.createSuperAdmin(
                new RegistrationDTO()
                        .firstname("Hanga")
                        .lastname("Wilfrid")
                        .dateOfBirth(LocalDate.now())
        );
    }
}
