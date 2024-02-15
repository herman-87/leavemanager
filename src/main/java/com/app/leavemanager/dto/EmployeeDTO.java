package com.app.leavemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private boolean isActivated = false;
}
