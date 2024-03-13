package com.app.leavemanager.domain.holiday;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Embeddable
@NoArgsConstructor
@Data
public class Period {
    @Column(name = "c_start_date")
    private LocalDate startDate;
    @Column(name = "c_end_date")
    private LocalDate endDate;

    public Period(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("start date is after end date");
        }
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getNumberOfDays() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public boolean isStarted() {
        return startDate.isBefore(LocalDate.now());
    }

    public boolean isPassed() {
        return endDate.isBefore(LocalDate.now());
    }
}
