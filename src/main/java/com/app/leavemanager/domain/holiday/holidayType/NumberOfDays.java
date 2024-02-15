package com.app.leavemanager.domain.holiday.holidayType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class NumberOfDays {

    @Column(name = "c_minimum_number_day")
    private int minimum;
    @Column(name = "c_maximum_number_day")
    private int maximum;
}
