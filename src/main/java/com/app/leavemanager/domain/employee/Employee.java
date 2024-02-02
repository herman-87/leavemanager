package com.app.leavemanager.domain.employee;

import com.app.leavemanager.domain.employee.user.Role;
import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayType;
import com.app.leavemanager.domain.holiday.Period;
import com.app.leavemanager.repository.dao.DefaultHolidayRepository;
import com.app.leavemanager.repository.dao.EmployeeRepository;
import com.app.leavemanager.repository.dao.HolidayRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private boolean isActivated = false;
    @OneToMany(mappedBy = "createdBy")
    @Builder.Default
    private List<Holiday> holidays = new ArrayList<>();

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
                        .isActivated(true)
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

    @Transactional
    public void validate(EmployeeRepository employeeRepository) {
        this.isActivated = true;
        employeeRepository.save(this);
    }

    public Holiday createHoliday(String title,
                              HolidayType type,
                              String description,
                              Period period,
                              HolidayRepository holidayRepository) {
        return holidayRepository.save(
                Holiday.builder()
                        .title(title)
                        .type(type)
                        .period(period)
                        .createdAt(LocalDateTime.now())
                        .createdBy(this)
                        .build()
        );
    }

    public Role getUserRole() {
        return this.user.getRole();
    }

    public boolean hasAuthorityOver(Holiday holiday) {
        return Role.EMPLOYEE.equals(this.getUserRole()) && holiday.isCreatedBy(this);
    }
}
