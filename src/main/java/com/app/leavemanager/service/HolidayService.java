package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.dto.HolidayTypeDTO;
import com.app.leavemanager.mapper.HolidayMapper;
import com.app.leavemanager.domain.employee.EmployeeRepository;
import com.app.leavemanager.domain.holiday.HolidayRepository;
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

    private static boolean isAuthorOf(Employee employee, Holiday holiday) {
        return employee.hasRoleEmployee() && holiday.isCreatedBy(employee);
    }

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
    public void updateHoliday(Long holidayId,
                              HolidayDTO holidayDTO,
                              String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (isAuthorOf(employee, holiday)) {
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
        employee.deleteHoliday(holiday, holidayRepository);
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public HolidayDTO getHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (
                employee.hasRoleSuperAdmin()
                        || employee.hasRoleAdmin()
                        || isAuthorOf(employee, holiday)) {

            return holidayMapper.toDTO(holiday);
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
        employee.approveHoliday(holiday, holidayRepository);
    }

    @Transactional
    public void publishHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.publishHoliday(holiday, holidayRepository);
    }

    @Transactional
    public void unapprovedHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.unapprovedHoliday(holiday, holidayRepository);
    }

    @Transactional
    public void unpublishedHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.unpublishedHoliday(holiday, holidayRepository);
    }

    private Employee getEmployeeByUsername(String currentUsername) {
        return employeeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }

    @Transactional
    public Long createHolidayType(HolidayTypeDTO holidayTypeDTO, String currentUsername) {

        Employee employee = getEmployeeByUsername(currentUsername);
        return employee.createHolidayType(
                holidayTypeDTO.getName(),
                holidayTypeDTO.getDescription(),
                holidayRepository
        ).getId();
    }

    @Transactional
    public List<HolidayTypeDTO> getAllHolidayTypes() {
        return holidayRepository.findAllHolidayTypes()
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }

    @Transactional
    public HolidayTypeDTO getHolidayTypeById(Long holidayId) {
        return holidayRepository.findHolidayTypeById(holidayId)
                .map(holidayMapper::toDTO)
                .orElseThrow();
    }

    @Transactional
    public void updateHolidayTypeById(Long holidayId, HolidayTypeDTO holidayTypeDTO) {

        HolidayType holidayType = fetchHolidayTypeById(holidayId);
        holidayType.update(
                holidayTypeDTO.getName(),
                holidayTypeDTO.getDescription(),
                holidayRepository
        );
    }

    private HolidayType fetchHolidayTypeById(Long holidayId) {
        return holidayRepository.findHolidayTypeById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday type Not Found"));
    }

    @Transactional
    public void deleteHolidayTypeById(Long holidayTypeId) {

        HolidayType holidayType = fetchHolidayTypeById(holidayTypeId);
        holidayType.delete(holidayRepository);
    }
}
