package com.app.leavemanager.DAO;

import com.app.leavemanager.domain.Holiday;
import com.app.leavemanager.repository.HolidaySpringRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DefaultHolidayRepository implements HolidayRepository {

    private final HolidaySpringRepository holidaySpringRepository;

    @Override
    public Holiday save(Holiday holiday) {
        return holidaySpringRepository.saveAndFlush(holiday);
    }
}
