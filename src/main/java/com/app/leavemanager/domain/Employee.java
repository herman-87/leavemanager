package com.app.leavemanager.domain;

import com.app.leavemanager.DAO.EmployeeDAO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private Integer id;
    @Column(name = "c_firstname")
    private String firstname;
    @Column(name = "c_lastname")
    private String lastname;
    @Column(name = "c_date_of_birth")
    private LocalDate dateOfBirth;

    public static Employee create(String firstname,
                                  String lastname,
                                  LocalDate dateOfBirth,
                                  EmployeeDAO employeeDAO) {
        return employeeDAO.save(
                Employee.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .dateOfBirth(dateOfBirth)
                        .build()
        );
    }

    public void update(String firstname,
                       String lastname,
                       LocalDate dateOfBirth,
                       EmployeeDAO employeeDAO) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        employeeDAO.save(this);
    }
}
