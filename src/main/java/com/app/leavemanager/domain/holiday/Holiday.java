package com.app.leavemanager.domain.holiday;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.notice.Notice;
import com.app.leavemanager.domain.holiday.notice.NoticeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_holiday")
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
    @ManyToOne
    @JoinColumn(name = "c_type", referencedColumnName = "c_id")
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
    @Builder.Default
    @OneToMany(mappedBy = "holiday")
    List<Notice> notices = new ArrayList<>();
    @Column(name = "c_reason")
    private String reason;

    public void update(HolidayType type,
                       String description,
                       String title,
                       Period period,
                       HolidayRepository holidayRepository) {

        this.period = period;
        this.type = type;
        this.title = title;
        this.description = description;
        holidayRepository.saveAndFlush(this);
    }

    public void notice(NoticeType noticeType,
                       String description,
                       Employee employee,
                       HolidayRepository holidayRepository) {
        if (isPublished()) {
            Notice notice = Notice.builder()
                    .type(noticeType)
                    .description(description)
                    .createdBy(employee)
                    .holiday(this)
                    .build();
            this.notices.add(notice);

            holidayRepository.saveAndFlush(notice);
        } else {
            log.error("this improvement is not possible");
        }
    }

    private boolean isPublished() {
        return HolidayStatus.PUBLISH.equals(this.status);
    }

    public void publish(HolidayRepository holidayRepository) {

        if (HolidayStatus.DRAFT.equals(this.status)) {
            this.status = HolidayStatus.PUBLISH;
            holidayRepository.saveAndFlush(this);
        } else {
            log.error("The publish is not possible");
        }
    }

    public void unpublished(HolidayRepository holidayRepository) {

        if (isPublished()) {
            this.status = HolidayStatus.DRAFT;
            holidayRepository.saveAndFlush(this);
        } else {
            log.error("this unpublished is not possible");
        }
    }

    public boolean isCreatedBy(Employee employee) {
        return this.createdBy.equals(employee);
    }

    public void delete(HolidayRepository holidayRepository) {
        holidayRepository.deleteById(this.id);
    }

    public boolean isBetween(long minimumOfDays, long maximumOfDays) {

        long numberOfDays = this.period.getNumberOfDays();
        if (minimumOfDays <= numberOfDays && numberOfDays <= maximumOfDays) {
            return true;
        } else {
            return false;
        }
    }

    public void start(HolidayRepository holidayRepository) {
        this.status = HolidayStatus.IN_PROGRESS;
        holidayRepository.saveAndFlush(this);
    }

    public void approve(String value, HolidayRepository holidayRepository) {
        this.status = HolidayStatus.VALIDATED;
        this.reason = value;
        holidayRepository.saveAndFlush(this);
    }

    public void reject(String value, HolidayRepository holidayRepository) {
        this.status = HolidayStatus.REFUSED;
        this.reason = value;
        holidayRepository.saveAndFlush(this);
    }

    public boolean isStarted() {
        return this.period.isStarted();
    }

    public void close(HolidayRepository holidayRepository) {
        this.status = HolidayStatus.PASSED;
        holidayRepository.saveAndFlush(this);
    }

    public boolean isPassed() {
        return this.period.isPassed();
    }

    public boolean isValidated() {
        return HolidayStatus.VALIDATED.equals(status);
    }

    public boolean isInProgress() {
        return HolidayStatus.IN_PROGRESS.equals(status);
    }
}
