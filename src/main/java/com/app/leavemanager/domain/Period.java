package com.app.leavemanager.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Period {
    @Column(name = "c_start_date")
    private LocalDate startDate;
    @Column(name = "c_end_date")
    private LocalDate endDate;
}
