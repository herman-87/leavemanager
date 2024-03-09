package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.EmployeeRepository;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.mapper.HolidayMapper;
import com.leavemanager.openapi.model.HolidayConfigDTO;
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
        HolidayType holidayType = getHolidayTypeById(holidayConfigDTO.getHolidayTypeId());

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

        return holidayRepository.findHolidayConfigById(holidayConfigId)
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

    @Transactional
    public void activateHolidayById(Long holidayConfigId) {
        HolidayConfig holidayConfig = fetchHolidayConfigById(holidayConfigId);
        holidayConfig.activate(holidayRepository);
    }

    private HolidayConfig fetchHolidayConfigById(Long holidayConfigId) {
        return holidayRepository.findHolidayConfigById(holidayConfigId)
                .orElseThrow(() -> new RuntimeException("holiday config Not Found"));
    }

    @Transactional
    public void deactivateHolidayById(Long holidayConfigId) {
        HolidayConfig holidayConfig = fetchHolidayConfigById(holidayConfigId);
        holidayConfig.deactivate(holidayRepository);
    }

    @Transactional
    public List<HolidayConfigDTO> getAllHolidayConfigsByHolidayType(Long holidayTypeId) {
        return holidayRepository.findAllHolidayConfigByHolidayTypeId(holidayTypeId)
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }

    @Transactional
    public HolidayConfigDTO getActivatedHolidayConfigsByHolidayType(Long holidayTypeId) {
        return holidayRepository.findHolidayConfigByTypeId(holidayTypeId)
                .map(holidayMapper::toDTO)
                .orElseThrow();
    }
}
