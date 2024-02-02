package com.app.leavemanager.domain.holiday;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.repository.dao.DefaultHolidayRepository;
import com.app.leavemanager.repository.dao.HolidayRepository;
import jakarta.persistence.*;
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
    private Long id;
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
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private HolidayStatus status = HolidayStatus.DRAFT;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "c_id")
    private Employee createdBy;

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

    public void publish(HolidayRepository holidayRepository) {

        if (HolidayStatus.DRAFT.equals(this.status)) {
            this.status = HolidayStatus.PUBLISH;
            holidayRepository.save(this);
        } else {
            log.error("The publish is not possible");
        }
    }

    public void unpublished(HolidayRepository holidayRepository) {

        if(HolidayStatus.PUBLISH.equals(this.status)) {
            this.status = HolidayStatus.DRAFT;
            holidayRepository.save(this);
        } else {
            log.error("this unpublished is not possible");
        }
    }

    public void unapprovedHoliday(HolidayRepository holidayRepository) {

        if (HolidayStatus.APPROVED.equals(this.status)) {
            this.status = HolidayStatus.PUBLISH;
            holidayRepository.save(this);
        } else {
            log.error("This operation is not possible");
        }
    }
}
