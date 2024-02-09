package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;

import java.util.List;
import java.util.Optional;

public interface HolidayRepository {

    Holiday save(Holiday holiday);
    HolidayType save(HolidayType holidayType);

    List<Holiday> findAll();
    List<HolidayType> findAllHolidayTypes();

    void deleteById(Long holidayId);

    Optional<Holiday> findById(Long holidayId);
    Optional<HolidayType> findHolidayStatusById(Long holidayId);
}
