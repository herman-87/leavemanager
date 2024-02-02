package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Role;
import com.app.leavemanager.repository.dao.DefaultHolidayRepository;
import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.repository.dao.EmployeeRepository;
import com.app.leavemanager.repository.dao.HolidayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private EmployeeRepository employeeRepository;

    @Transactional
    public Long createHoliday(HolidayDTO holidayDTO, String currentUsername) {

        Employee employee = getEmployeeByUsername(currentUsername);
        return employee.createHoliday(
                holidayDTO.getTitle(),
                holidayDTO.getType(),
                holidayDTO.getDescription(),
                holidayDTO.getPeriod(),
                holidayRepository
        ).getId();
    }

    @Transactional
    public List<HolidayDTO> getAllHoliday() {
        return holidayRepository.findAll()
                .stream()
                .map(holiday ->
                        HolidayDTO.builder()
                                .id(holiday.getId())
                                .title(holiday.getTitle())
                                .type(holiday.getType())
                                .status(holiday.getStatus())
                                .description(holiday.getDescription())
                                .period(holiday.getPeriod())
                                .createdAt(holiday.getCreatedAt())
                                .build()
                )
                .toList();
    }

    @Transactional
    public void updateHoliday(Long holidayId, HolidayDTO holidayDTO, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (Role.EMPLOYEE.equals(employee.getUserRole()) && holiday.isCreatedBy(employee)) {
            holiday.update(
                    holidayDTO.getType(),
                    holidayDTO.getDescription(),
                    holidayDTO.getTitle(),
                    holidayDTO.getPeriod(),
                    new DefaultHolidayRepository(holidayRepository)
            );
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void deleteHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (Role.EMPLOYEE.equals(employee.getUserRole()) && holiday.isCreatedBy(employee)) {
            holidayRepository.deleteById(holidayId);
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public HolidayDTO getHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        Role employeeRole = employee.getUser().getRole();

        if (
                Role.SUPER_ADMIN.equals(employeeRole)
                || Role.ADMIN.equals(employeeRole)
                || (Role.EMPLOYEE.equals(employeeRole) && holiday.isCreatedBy(employee))
        ) {
            return HolidayDTO
                    .builder()
                    .id(holiday.getId())
                    .type(holiday.getType())
                    .title(holiday.getTitle())
                    .description(holiday.getDescription())
                    .createdAt(holiday.getCreatedAt())
                    .period(holiday.getPeriod())
                    .status(holiday.getStatus())
                    .build();
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    private Holiday getHolidayById(Long holidayId) {
        return holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday Not Found"));
    }

    @Transactional
    public void approveHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (employee.hasAuthorityOver(holiday)) {
            holiday.approve(new DefaultHolidayRepository(holidayRepository));
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void publishHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (employee.hasAuthorityOver(holiday)) {
            holiday.publish(new DefaultHolidayRepository(holidayRepository));
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void unapprovedHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (employee.hasAuthorityOver(holiday)) {
            holiday.unapprovedHoliday(holidayRepository);
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void unpublishedHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (employee.hasAuthorityOver(holiday)) {
            holiday.unpublished(holidayRepository);
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    private Employee getEmployeeByUsername(String currentUsername) {
        return employeeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }
}
