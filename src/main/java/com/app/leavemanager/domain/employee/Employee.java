package com.app.leavemanager.domain.employee;

import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.repository.dao.EmployeeRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    private Long id;
    @Column(name = "c_firstname")
    private String firstname;
    @Column(name = "c_lastname")
    private String lastname;
    @Column(name = "c_date_of_birth")
    private LocalDate dateOfBirth;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "c_user", referencedColumnName = "c_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "c_created_by", referencedColumnName = "c_id")
    private Employee createdBy;
    @Builder.Default
    @Column(name = "c_activated")
    private boolean isActivated = true;

    private static int numberOfEmailAddressGenerated = 1;


    public static Employee create(String firstname,
                                  String lastname,
                                  LocalDate dateOfBirth,
                                  User user,
                                  EmployeeRepository employeeRepository) {
        return employeeRepository.save(
                Employee.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .dateOfBirth(dateOfBirth)
                        .user(user)
                        .build()
        );
    }

    public static Employee createSuperAdmin(String firstname,
                                            String lastname,
                                            LocalDate dateOfBirth,
                                            User user,
                                            EmployeeRepository employeeRepository) {
        return employeeRepository.save(
                Employee.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .dateOfBirth(dateOfBirth)
                        .user(user)
                        .build()
        );
    }

    public static String generatedEmail(String firstname, String lastname, String emailSuffix) {
        String generatedEmail =
                firstname
                        .concat(".")
                        .concat(lastname)
                        .concat(String.valueOf(numberOfEmailAddressGenerated))
                        .concat(emailSuffix)
                        .replaceAll("\\s", "");
        numberOfEmailAddressGenerated++;
        return generatedEmail;
    }

    public void update(String email,
                       String password,
                       String firstname,
                       String lastname,
                       LocalDate dateOfBirth,
                       EmployeeRepository employeeDAO) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.user = User.builder()
                .email(email)
                .password(password)
                .build();
        employeeDAO.save(this);
    }

    public void setUser(User user, EmployeeRepository employeeRepository) {
        this.user = user;
        employeeRepository.save(this);
    }

    public Employee createEmployee(String firstname,
                                   String lastname,
                                   LocalDate dateOfBirth,
                                   User user,
                                   EmployeeRepository employeeRepository) {
        return employeeRepository.save(
                Employee.builder()
                        .firstname(firstname)
                        .lastname(lastname)
                        .dateOfBirth(dateOfBirth)
                        .user(user)
                        .createdBy(this)
                        .build()
        );
    }
}
