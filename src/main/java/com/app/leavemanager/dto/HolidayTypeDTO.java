package com.app.leavemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolidayTypeDTO {

    private Long id;
    private String name;
    private String description;
}
