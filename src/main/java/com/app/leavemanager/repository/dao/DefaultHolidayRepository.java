package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.repository.spring.HolidaySpringRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultHolidayRepository implements HolidayRepository {

    private final HolidaySpringRepository holidaySpringRepository;

    @Override
    public Holiday save(Holiday holiday) {
        return holidaySpringRepository.saveAndFlush(holiday);
    }
}
