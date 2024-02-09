package com.app.leavemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class HolidayConfigDTO {

    private Long id;
    private String description;
    private int numberOfTime;
    private int minimumOfDays;
    private int maximumOfDays;
    private HolidayTypeDTO typeDTO;
}
