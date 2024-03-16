package com.app.leavemanager.domain.holiday.config;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayRepository;
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
    private long numberOfTime;
    @Column(name = "c_minimum_of_day")
    private long minimumOfDays;
    @Column(name = "c_maximum_of_day")
    private long maximumOfDays;
    @Column(name = "c_description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "c_holiday_type", referencedColumnName = "c_id")
    private HolidayType type;
    @Column(name = "c_is_active")
    @Builder.Default
    private boolean isActivate = false;

    public boolean isRespectedBy(Holiday holidayToCreate, long numberOfHolidayPassed) {
        return holidayToCreate.isBetween(minimumOfDays, maximumOfDays)
                && numberOfHolidayPassed < numberOfTime;
    }

    public void activate(HolidayRepository holidayRepository) {
        if (holidayRepository.holidayConfigExistByTypeAndIsActivateTrue(this.type.getId())) {
            throw new RuntimeException("Activated config already exist");
        } else {
            this.isActivate = true;
            holidayRepository.saveAndFlush(this);
        }
    }

    public void deactivate(HolidayRepository holidayRepository) {
        if (this.isActivate) {
            this.isActivate = false;
            holidayRepository.saveAndFlush(this);
        }
    }
}
