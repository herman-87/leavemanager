package com.app.leavemanager.repository.spring;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidaySpringRepository extends JpaRepository<Holiday, Long> {

    boolean existsByTypeId(Long typeId);

    List<Holiday> findAllByStatusAndPeriodEndDateBefore(HolidayStatus status, LocalDate currentTime);
    List<Holiday> findAllByStatusAndPeriodStartDateEquals(HolidayStatus status, LocalDate currentDate);
    List<Holiday> findAllByCreatedById(Long createdById);
    List<Holiday> findAllByStatusIsNot(HolidayStatus status);

    List<Holiday> findAllByStatus(HolidayStatus holidayStatus);
}
