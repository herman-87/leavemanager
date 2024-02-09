package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.config.HolidayConfigRepository;
import com.app.leavemanager.repository.spring.HolidayConfigSpringRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultHolidayConfigRepository implements HolidayConfigRepository {

    private final HolidayConfigSpringRepository holidayConfigSpringRepository;

    @Override
    public HolidayConfig save(HolidayConfig holidayConfig) {
        return holidayConfigSpringRepository.save(holidayConfig);
    }
}
