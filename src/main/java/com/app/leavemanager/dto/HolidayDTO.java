package com.app.leavemanager.dto;

import com.app.leavemanager.domain.holiday.HolidayStatus;
import com.app.leavemanager.domain.holiday.Period;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayDTO {

    private Long id;
    private String title;
    private HolidayType type;
    private String description;
    private LocalDateTime createdAt;
    private Period period;
    private HolidayStatus status;
}
