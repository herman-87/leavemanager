package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Role;
import com.app.leavemanager.repository.dao.DefaultHolidayRepository;
import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.repository.dao.EmployeeRepository;
import com.app.leavemanager.repository.spring.HolidaySpringRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidaySpringRepository holidaySpringRepository;
    private EmployeeRepository employeeRepository;

    @Transactional
    public Long createHoliday(HolidayDTO holidayDTO, String currentUsername) {

        Employee employee = getEmployeeByUsername(currentUsername);
        return employee.createHoliday(
                holidayDTO.getTitle(),
                holidayDTO.getType(),
                holidayDTO.getDescription(),
                holidayDTO.getPeriod(),
                new DefaultHolidayRepository(holidaySpringRepository)
        ).getId();
    }

    @Transactional
    public List<HolidayDTO> getAllHoliday() {
        return holidaySpringRepository.findAll()
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
                    new DefaultHolidayRepository(holidaySpringRepository)
            );
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void deleteHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (Role.EMPLOYEE.equals(employee.getUserRole()) && holiday.isCreatedBy(employee)) {
            holidaySpringRepository.deleteById(holidayId);
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

    private Holiday getHolidayById(Long holidayId) {
        return holidaySpringRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday Not Found"));
    }

    @Transactional
    public void approveHoliday(Integer holidayId) {
        holidaySpringRepository.findById(holidayId)
                .ifPresent(holiday -> holiday.approve(new DefaultHolidayRepository(holidaySpringRepository)));
    }

    @Transactional
    public void publishHoliday(Integer holidayId) {
        holidaySpringRepository.findById(holidayId)
                .ifPresent(holiday -> holiday.publish(new DefaultHolidayRepository(holidaySpringRepository)));
    }

    public void unapprovedHoliday(Integer holidayId) {
        holidaySpringRepository.findById(holidayId)
                .ifPresent(holiday -> holiday.unapprovedHoliday(new DefaultHolidayRepository(holidaySpringRepository)));
    }

    public void unpublishedHoliday(Integer holidayId) {
        holidaySpringRepository.findById(holidayId)
                .ifPresent(holiday -> holiday.unpublished(new DefaultHolidayRepository(holidaySpringRepository)));
    }

    private Employee getEmployeeByUsername(String currentUsername) {
        return employeeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }
}
