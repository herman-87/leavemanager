package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.EmployeeRepository;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.dto.HolidayConfigDTO;
import com.app.leavemanager.mapper.HolidayMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayConfigService {
    
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;

    @Transactional
    public Long create(HolidayConfigDTO holidayConfigDTO, String currentUsername) {

        Employee employee = getEmployeeByUsername(currentUsername);
        HolidayType holidayType = getHolidayTypeById(holidayConfigDTO.getTypeDTO().getId());

        return employee.createHolidayConfig(
                holidayConfigDTO.getDescription(),
                holidayConfigDTO.getNumberOfTime(),
                holidayConfigDTO.getMinimumOfDays(),
                holidayConfigDTO.getMaximumOfDays(),
                holidayType,
                holidayRepository
        ).getId();
    }

    private HolidayType getHolidayTypeById(Long id) {
        return holidayRepository
                .findHolidayTypeById(id)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }

    private Employee getEmployeeByUsername(String currentUsername) {
        return employeeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }

    @Transactional
    public HolidayConfigDTO getHolidayConfigById(Long holidayConfigId) {

        return holidayRepository.findHolidayConfigByI(holidayConfigId)
                .map(holidayMapper::toDTO)
                .orElseThrow(() -> new Error("Holiday config not found"));

    }

    @Transactional
    public List<HolidayConfigDTO> getAllHolidayConfigs() {
        return holidayRepository.findAllHolidayConfig()
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }
}