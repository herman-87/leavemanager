package com.app.leavemanager.domain.holiday.config;

import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "t_holiday_config")
public class HolidayConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private Long id;
    @Column(name = "c_number_of_time")
    private int numberOfTime;
    @Column(name = "c_minimum_of_day")
    private int minimumOfDays;
    @Column(name = "c_maximum_of_day")
    private int maximumOfDays;
    @Column(name = "c_description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "c_holiday_type", referencedColumnName = "c_id")
    private HolidayType type;
}
