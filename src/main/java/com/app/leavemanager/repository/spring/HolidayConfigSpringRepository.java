package com.app.leavemanager.repository.spring;

import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HolidayConfigSpringRepository extends JpaRepository<HolidayConfig, Long> {

    Optional<HolidayConfig> findByTypeId(Long id);
}
