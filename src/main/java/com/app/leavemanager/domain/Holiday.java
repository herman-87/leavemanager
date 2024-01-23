package com.app.leavemanager.domain;

import com.app.leavemanager.DAO.DefaultHolidayRepository;
import com.app.leavemanager.DAO.HolidayRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Table(name = "c_holiday")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
@Slf4j
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private Integer id;
    @Column(name = "c_title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "c_type")
    private HolidayType type;
    @Column(name = "c_description")
    private String description;
    @Column(name = "c_created_at")
    private LocalDateTime createdAt;
    @Embedded
    private Period period;
    @Enumerated(value = EnumType.STRING)
    private HolidayStatus status;

    public static Holiday create(String title,
                                 HolidayType type,
                                 String description,
                                 Period period,
                                 DefaultHolidayRepository defaultHolidayRepository) {

        return defaultHolidayRepository.save(
                Holiday.builder()
                        .title(title)
                        .type(type)
                        .description(description)
                        .period(period)
                        .status(HolidayStatus.DRAFT)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    public void update(HolidayType type,
                       String description,
                       String title,
                       Period period,
                       HolidayRepository holidayRepository) {

        this.period = period;
        this.type = type;
        this.title = title;
        this.description = description;
        holidayRepository.save(this);
    }

    public void approve(HolidayRepository holidayRepository) {

        if (HolidayStatus.PUBLISH.equals(this.status)) {
            this.status = HolidayStatus.APPROVED;
            holidayRepository.save(this);
        } else {
            log.error("this improvement is not possible");
        }
    }
}
