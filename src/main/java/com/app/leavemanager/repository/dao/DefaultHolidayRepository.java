package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.HolidayStatus;
import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.notice.Notice;
import com.app.leavemanager.repository.spring.HolidayConfigSpringRepository;
import com.app.leavemanager.repository.spring.HolidaySpringRepository;
import com.app.leavemanager.repository.spring.HolidayTypeSpringRepository;
import com.app.leavemanager.repository.spring.NoticeSpringRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultHolidayRepository implements HolidayRepository {

    private final HolidaySpringRepository holidaySpringRepository;
    private final HolidayTypeSpringRepository holidayTypeSpringRepository;
    private final HolidayConfigSpringRepository holidayConfigSpringRepository;
    private final NoticeSpringRepository noticeSpringRepository;

    @Override
    public Holiday saveAndFlush(Holiday holiday) {
        return holidaySpringRepository.saveAndFlush(holiday);
    }

    @Override
    public HolidayType saveAndFlush(HolidayType holidayType) {
        return holidayTypeSpringRepository.save(holidayType);
    }

    @Override
    public List<Holiday> findAll() {
        return holidaySpringRepository.findAll();
    }

    @Override
    public List<Holiday> findAllByStatusIsNot(HolidayStatus status) {
        return holidaySpringRepository.findAllByStatusIsNot(status);
    }

    @Override
    public List<HolidayType> findAllHolidayTypes() {
        return holidayTypeSpringRepository.findAll();
    }

    @Override
    public void deleteById(Long holidayId) {
        holidaySpringRepository.deleteById(holidayId);
    }

    @Override
    public void deleteHolidayTypeById(Long holidayTypeId) {
        holidayTypeSpringRepository.deleteById(holidayTypeId);
    }

    @Override
    public Optional<Holiday> findById(Long holidayId) {
        return holidaySpringRepository.findById(holidayId);
    }

    @Override
    public Optional<HolidayType> findHolidayTypeById(Long holidayTypeId) {
        return holidayTypeSpringRepository.findById(holidayTypeId);
    }

    @Override
    public List<Notice> findAllNoticeByHolidayId(Long holidayId) {
        return noticeSpringRepository.findAllByHolidayId(holidayId);
    }

    @Override
    public boolean existHolidayByTypeId(Long holidayTypeId) {
        return holidaySpringRepository.existsByTypeId(holidayTypeId);
    }

    @Override
    public Optional<HolidayConfig> findHolidayConfigById(Long holidayConfigId) {
        return holidayConfigSpringRepository.findById(holidayConfigId);
    }

    @Override
    public HolidayConfig saveAndFlush(HolidayConfig holidayConfig) {
        return holidayConfigSpringRepository.saveAndFlush(holidayConfig);
    }

    @Override
    public List<HolidayConfig> findAllHolidayConfig() {
        return holidayConfigSpringRepository.findAll();
    }

    @Override
    public List<HolidayConfig> findAllHolidayConfigByHolidayTypeId(Long holidayTypeId) {
        return holidayConfigSpringRepository.findHolidayConfigByTypeId(holidayTypeId);
    }

    @Override
    public Optional<HolidayConfig> findHolidayConfigByTypeId(Long typeId) {
        return holidayConfigSpringRepository.findFirstByTypeIdAndIsActivateTrue(typeId);
    }

    @Override
    public List<Holiday> findAllHolidayByStatusAndPeriodEndDateIsBefore(HolidayStatus status, LocalDate currentDate) {
        return holidaySpringRepository.findAllByStatusAndPeriodEndDateBefore(status, currentDate);
    }

    @Override
    public List<Holiday> findAllByStatusAndPeriodStartDateEquals(HolidayStatus status, LocalDate currentDate) {
        return holidaySpringRepository.findAllByStatusAndPeriodStartDateEquals(status, currentDate);
    }

    @Override
    public Notice saveAndFlush(Notice notice) {
        return noticeSpringRepository.save(notice);
    }

    @Override
    public List<Holiday> findAllByCreatedById(Long id) {
        return holidaySpringRepository.findAllByCreatedById(id);
    }

    @Override
    public boolean holidayConfigExistByTypeAndIsActivateTrue(Long holidayTypeId) {
        return holidayConfigSpringRepository.existsByTypeIdAndIsActivateTrue(holidayTypeId);
    }

    @Override
    public List<Holiday> findAllByStatus(HolidayStatus holidayStatus) {
        return holidaySpringRepository.findAllByStatus(holidayStatus);
    }
}
