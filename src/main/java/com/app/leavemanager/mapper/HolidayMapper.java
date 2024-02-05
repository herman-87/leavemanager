package com.app.leavemanager.mapper;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.dto.HolidayDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

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
}
