package com.app.leavemanager.domain.holiday;

import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.notice.Notice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayRepository {

    Holiday save(Holiday holiday);

    HolidayType save(HolidayType holidayType);

    List<Holiday> findAll();

    List<HolidayType> findAllHolidayTypes();

    void deleteById(Long holidayId);

    void deleteHolidayTypeById(Long holidayId);

    Optional<Holiday> findById(Long holidayId);

    Optional<HolidayType> findHolidayTypeById(Long holidayId);
    List<Notice> findAllNoticeByHolidayId(Long holidayId);

    boolean existHolidayByTypeId(Long holidayTypeId);

    Optional<HolidayConfig> findHolidayConfigById(Long holidayConfigId);

    HolidayConfig save(HolidayConfig build);

    List<HolidayConfig> findAllHolidayConfig();
    List<HolidayConfig> findAllHolidayConfigByHolidayTypeId(Long holidayTypeId);

    Optional<HolidayConfig> findHolidayConfigByTypeId(Long id);

    List<Holiday> findAllHolidayByStatusAndPeriodEndDateIsBefore(HolidayStatus status, LocalDate date);
    List<Holiday> findAllHolidayByStatusAndPeriodStartDateIsBefore(HolidayStatus status, LocalDate date);

    Notice save(Notice notice);

    List<Holiday> findAllByCreatedById(Long id);
}
