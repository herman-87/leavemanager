package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.user.Scope;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.mapper.HolidayMapper;
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
    private final EmployeeRepository employeeRepository;
    private final HolidayMapper holidayMapper;

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
    public List<HolidayDTO> getAllHolidays(String currentUsername) {
        return holidayRepository.findAll()
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }

    @Transactional
    public void updateHoliday(Long holidayId, HolidayDTO holidayDTO, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (Scope.EMPLOYEE.equals(employee.getUserRole()) && holiday.isCreatedBy(employee)) {
            holiday.update(
                    holidayDTO.getType(),
                    holidayDTO.getDescription(),
                    holidayDTO.getTitle(),
                    holidayDTO.getPeriod(),
                    holidayRepository
            );
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void deleteHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (Scope.EMPLOYEE.equals(employee.getUserRole()) && holiday.isCreatedBy(employee)) {
            holidayRepository.deleteById(holidayId);
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public HolidayDTO getHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        Scope employeeRole = employee.getUser().getRole();

        if (
                Scope.SUPER_ADMIN.equals(employeeRole)
                        || Scope.ADMIN.equals(employeeRole)
                        || (Scope.EMPLOYEE.equals(employeeRole) && holiday.isCreatedBy(employee))
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
            holiday.approve(holidayRepository);
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public void publishHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (employee.hasAuthorityOver(holiday)) {
            holiday.publish(holidayRepository);
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
