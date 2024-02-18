package com.app.leavemanager.repository.spring;

import com.app.leavemanager.domain.holiday.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeSpringRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByHolidayId(Long holidayId);
}
