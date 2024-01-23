package com.app.leavemanager.service;

import com.app.leavemanager.DAO.DefaultHolidayRepository;
import com.app.leavemanager.DTO.HolidayDTO;
import com.app.leavemanager.domain.Holiday;
import com.app.leavemanager.repository.HolidaySpringRepository;
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

    @Transactional
    public Integer createHoliday(HolidayDTO holidayDTO) {

        return Holiday.create(
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
    public void updateHoliday(Integer holidayId, HolidayDTO holidayDTO) {

        Holiday holiday = holidaySpringRepository.findById(holidayId).orElseThrow();
        holiday.update(
                holidayDTO.getType(),
                holidayDTO.getDescription(),
                holidayDTO.getTitle(),
                holidayDTO.getPeriod(),
                new DefaultHolidayRepository(holidaySpringRepository)
        );
    }

    @Transactional
    public void deleteHoliday(Integer employeeId) {
        holidaySpringRepository.deleteById(employeeId);
    }

    @Transactional
    public HolidayDTO getHolidayById(Integer holidayId) {
        return holidaySpringRepository.findById(holidayId)
                .map(holiday -> HolidayDTO
                        .builder()
                        .id(holiday.getId())
                        .type(holiday.getType())
                        .title(holiday.getTitle())
                        .description(holiday.getDescription())
                        .createdAt(holiday.getCreatedAt())
                        .period(holiday.getPeriod())
                        .status(holiday.getStatus())
                        .build()
                )
                .orElseThrow();
    }

    @Transactional
    public void approveHoliday(Integer holidayId) {
        holidaySpringRepository.findById(holidayId)
                .ifPresent(holiday -> holiday.approve(new DefaultHolidayRepository(holidaySpringRepository)));
    }

    @Transactional
    public void publish(Integer holidayId) {
        holidaySpringRepository.findById(holidayId)
                .ifPresent(holiday -> holiday.publish(new DefaultHolidayRepository(holidaySpringRepository)));
    }
}
