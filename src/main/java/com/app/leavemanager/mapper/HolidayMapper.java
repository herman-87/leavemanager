package com.app.leavemanager.mapper;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.Period;
import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.notice.NoticeType;
import com.leavemanager.openapi.model.HolidayConfigDTO;
import com.leavemanager.openapi.model.HolidayDTO;
import com.leavemanager.openapi.model.HolidayTypeDTO;
import com.leavemanager.openapi.model.NoticeStatusDTO;
import com.leavemanager.openapi.model.PeriodDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
@Component
public interface HolidayMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "title")
    @Mapping(target = "type")
    @Mapping(target = "description")
    @Mapping(target = "createdAt")
    @Mapping(target = "period")
    @Mapping(target = "status")
    HolidayDTO toDTO(Holiday holiday);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "name")
    @Mapping(target = "description")
    HolidayTypeDTO toDTO(HolidayType holidayType);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id")
    @Mapping(target = "description")
    @Mapping(target = "numberOfTime")
    @Mapping(target = "minimumOfDays")
    @Mapping(target = "maximumOfDays")
    @Mapping(target = "holidayTypeId", source = "type.id")
    HolidayConfigDTO toDTO(HolidayConfig holidayConfig);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "startDate")
    @Mapping(target = "endDate")
    Period toDTO(PeriodDTO value);

    default OffsetDateTime map(LocalDateTime value) {
        ZoneId zoneId = ZoneId.systemDefault();
        return value.atZone(zoneId).toOffsetDateTime();
    }

    @BeanMapping(ignoreByDefault = true)
    NoticeType fromDTO(NoticeStatusDTO type);
}
