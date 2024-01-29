package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;

public interface HolidayRepository {

    Holiday save(Holiday holiday);
}
