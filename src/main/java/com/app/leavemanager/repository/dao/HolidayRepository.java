package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.Holiday;

public interface HolidayRepository {

    Holiday save(Holiday holiday);
}
