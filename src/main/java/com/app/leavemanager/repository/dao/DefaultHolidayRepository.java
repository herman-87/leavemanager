package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.config.HolidayConfig;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.repository.spring.HolidayConfigSpringRepository;
import com.app.leavemanager.repository.spring.HolidaySpringRepository;
import com.app.leavemanager.repository.spring.HolidayTypeSpringRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultHolidayRepository implements HolidayRepository {

    private final HolidaySpringRepository holidaySpringRepository;
    private final HolidayTypeSpringRepository holidayTypeSpringRepository;
    private final HolidayConfigSpringRepository holidayConfigSpringRepository;

    @Override
    public Holiday save(Holiday holiday) {
        return holidaySpringRepository.saveAndFlush(holiday);
    }

    @Override
    public HolidayType save(HolidayType holidayType) {
        return holidayTypeSpringRepository.save(holidayType);
    }

    @Override
    public List<Holiday> findAll() {
        return holidaySpringRepository.findAll();
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
    public Optional<HolidayType> findHolidayTypeById(Long holidayId) {
        return holidayTypeSpringRepository.findById(holidayId);
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
    public HolidayConfig save(HolidayConfig holidayConfig) {
        return holidayConfigSpringRepository.save(holidayConfig);
    }

    @Override
    public List<HolidayConfig> findAllHolidayConfig() {
        return holidayConfigSpringRepository.findAll();
    }

    @Override
    public Optional<HolidayConfig> findHolidayConfigByTypeId(Long typeId) {
        return holidayConfigSpringRepository.findByTypeId(typeId);
    }
}
