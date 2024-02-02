package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;

import java.util.List;
import java.util.Optional;

public interface HolidayRepository {

    Holiday save(Holiday holiday);

    List<Holiday> findAll();

    void deleteById(Long holidayId);

    Optional<Holiday> findById(Long holidayId);
}
