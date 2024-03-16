package com.app.leavemanager.domain.holiday;

import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.notice.Notice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HolidayRepository {

    Holiday saveAndFlush(Holiday holiday);

    HolidayType saveAndFlush(HolidayType holidayType);

    List<Holiday> findAll();

    List<Holiday> findAllByStatusIsNot(HolidayStatus status);

    List<HolidayType> findAllHolidayTypes();

    void deleteById(Long holidayId);

    void deleteHolidayTypeById(Long holidayId);

    Optional<Holiday> findById(Long holidayId);

    Optional<HolidayType> findHolidayTypeById(Long holidayId);
    List<Notice> findAllNoticeByHolidayId(Long holidayId);

    boolean existHolidayByTypeId(Long holidayTypeId);

    Optional<HolidayConfig> findHolidayConfigById(Long holidayConfigId);

    HolidayConfig saveAndFlush(HolidayConfig build);


    List<HolidayConfig> findAllHolidayConfig();
    List<HolidayConfig> findAllHolidayConfigByHolidayTypeId(Long holidayTypeId);

    Optional<HolidayConfig> findHolidayConfigByTypeId(Long id);

    List<Holiday> findAllHolidayByStatusAndPeriodEndDateIsBefore(HolidayStatus status, LocalDate date);
    List<Holiday> findAllByStatusAndPeriodStartDateEquals(HolidayStatus status, LocalDate date);

    Notice saveAndFlush(Notice notice);

    List<Holiday> findAllByCreatedById(Long id);

    boolean holidayConfigExistByTypeAndIsActivateTrue(Long holidayTypeId);

    List<Holiday> findAllByStatus(HolidayStatus holidayStatus);
}
