package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.EmployeeRepository;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.config.HolidayConfigRepository;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.dto.HolidayConfigDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HolidayConfigService {
    
    private final HolidayConfigRepository holidayConfigRepository;
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;

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
                holidayConfigRepository
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
}
