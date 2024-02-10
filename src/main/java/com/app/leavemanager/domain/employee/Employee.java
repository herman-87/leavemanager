package com.app.leavemanager.domain.employee;

import com.app.leavemanager.domain.employee.user.Scope;
import com.app.leavemanager.domain.employee.user.User;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.Period;
import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    private static int numberOfEmailAddressGenerated = 1;
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
                        .description(description)
                        .period(period)
                        .createdAt(LocalDateTime.now())
                        .createdBy(this)
                        .build()
        );
    }

    public Scope getUserRole() {
        return this.user.getRole();
    }

    public boolean hasRoleEmployee() {
        return user.hasRole(Scope.EMPLOYEE);
    }

    public boolean hasRoleSuperAdmin() {
        return user.hasRole(Scope.SUPER_ADMIN);
    }

    public boolean hasRoleAdmin() {
        return user.hasRole(Scope.ADMIN);
    }

    public void approveHoliday(Holiday holiday, HolidayRepository holidayRepository) {
        if (this.hasRoleEmployee()) {
            holiday.approve(holidayRepository);
        } else {
            throw new RuntimeException("Forbidden for the current user");
        }
    }

    public void publishHoliday(Holiday holiday, HolidayRepository holidayRepository) {
        if (hasRoleEmployee() && holiday.isCreatedBy(this)) {
            holiday.publish(holidayRepository);
        } else {
            throw new RuntimeException("Forbidden for the current user");
        }
    }

    public void unapprovedHoliday(Holiday holiday, HolidayRepository holidayRepository) {
        if (hasRoleEmployee()) {
            holiday.unapprovedHoliday(holidayRepository);
        } else {
            throw new RuntimeException("Forbidden for the current user");
        }
    }

    public void unpublishedHoliday(Holiday holiday, HolidayRepository holidayRepository) {
        if (hasRoleEmployee() && holiday.isCreatedBy(this)) {
            holiday.unpublished(holidayRepository);
        } else {
            throw new RuntimeException("Forbidden for the current user");
        }
    }

    public void deleteHoliday(Holiday holiday, HolidayRepository holidayRepository) {
        if (hasRoleEmployee() && holiday.isCreatedBy(this)) {
            holiday.delete(holidayRepository);
        } else {
            throw new RuntimeException("Forbidden for the current user");
        }
    }

    public HolidayType createHolidayType(String name,
                                         String description,
                                         HolidayRepository holidayRepository) {
        return holidayRepository.save(
                HolidayType.builder()
                        .name(name)
                        .description(description)
                        .createdBy(this)
                        .build()
        );
    }

    public HolidayConfig createHolidayConfig(String description,
                                             int numberOfTime,
                                             int minimumOfDays,
                                             int maximumOfDays,
                                             HolidayType holidayType,
                                             HolidayRepository holidayRepository) {
        return holidayRepository.save(
                HolidayConfig.builder()
                        .description(description)
                        .numberOfTime(numberOfTime)
                        .minimumOfDays(minimumOfDays)
                        .maximumOfDays(maximumOfDays)
                        .type(holidayType)
                        .build()
        );
    }
}
