package com.app.leavemanager.domain.holiday;

import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;

import javax.swing.text.html.Option;
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

    boolean existHolidayByTypeId(Long holidayTypeId);

    Optional<HolidayConfig> findHolidayConfigByI(Long holidayConfigId);

    HolidayConfig save(HolidayConfig build);

    List<HolidayConfig> findAllHolidayConfig();

    Optional<HolidayConfig> findHolidayConfigByTypeId(Long id);
}
