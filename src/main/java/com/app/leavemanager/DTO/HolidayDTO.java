package com.app.leavemanager.DTO;

import com.app.leavemanager.domain.HolidayType;
import com.app.leavemanager.domain.Period;
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

    private Integer id;
    private String title;
    private HolidayType type;
    private String description;
    private LocalDateTime createdAt;
    private Period period;
}
