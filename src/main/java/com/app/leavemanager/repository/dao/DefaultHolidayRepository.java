package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.repository.spring.HolidaySpringRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DefaultHolidayRepository implements HolidayRepository {

    private final HolidaySpringRepository holidaySpringRepository;

    @Override
    public Holiday save(Holiday holiday) {
        return holidaySpringRepository.saveAndFlush(holiday);
    }

    @Override
    public List<Holiday> findAll() {
        return holidaySpringRepository.findAll();
    }

    @Override
    public void deleteById(Long holidayId) {
        holidaySpringRepository.deleteById(holidayId);
    }
}
