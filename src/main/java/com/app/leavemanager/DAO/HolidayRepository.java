package com.app.leavemanager.DAO;

import com.app.leavemanager.domain.Holiday;

public interface HolidayRepository {

    Holiday save(Holiday holiday);
}
