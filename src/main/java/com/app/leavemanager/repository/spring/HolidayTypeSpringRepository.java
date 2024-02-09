package com.app.leavemanager.repository.spring;

import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayTypeSpringRepository extends JpaRepository<HolidayType, Long> {
}
