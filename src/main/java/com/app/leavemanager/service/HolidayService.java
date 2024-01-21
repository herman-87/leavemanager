package com.app.leavemanager.service;

import com.app.leavemanager.domain.Employee;
import com.app.leavemanager.domain.Holiday;
import com.app.leavemanager.dto.EmployeeDTO;
import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.repository.HolidayRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;

    @Transactional
    public Integer createHoliday(HolidayDTO holidayDTO) {

        Holiday holiday = Holiday.builder()
                .title(holidayDTO.getTitle())
                .type(holidayDTO.getType())
                .description(holidayDTO.getDescription())
                .period(holidayDTO.getPeriod())
                .createdAt(LocalDateTime.now())
                .build();

        return holidayRepository.saveAndFlush(holiday).getId();
    }

    @Transactional
    public List<HolidayDTO> getAllHoliday() {
        return holidayRepository.findAll()
                .stream()
                .map(holiday ->
                        HolidayDTO.builder()
                        .title(holiday.getTitle())
                        .type(holiday.getType())
                        .description(holiday.getDescription())
                        .period(holiday.getPeriod())
                        .createdAt(holiday.getCreatedAt())
                        .build()
                )
                .toList();
    }

    @Transactional
    public void updateHoliday(HolidayDTO holidayDTO) {
        Holiday holiday = holidayRepository.findById(holidayDTO.getId())
                .orElseThrow();
        holiday.update(
                holidayDTO.getType(),
                holidayDTO.getDescription(),
                holidayDTO.getTitle(),
                holidayDTO.getPeriod()
        );
        holidayRepository.save(holiday);
    }

    @Transactional
    public void deleteHoliday(Integer employeeId) {
        holidayRepository.deleteById(employeeId);
    }
}
