package com.app.leavemanager.repository.dao;

import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.repository.spring.HolidaySpringRepository;
import com.app.leavemanager.repository.spring.HolidayTypeSpringRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DefaultHolidayRepository implements HolidayRepository {

    private final HolidaySpringRepository holidaySpringRepository;
    private final HolidayTypeSpringRepository holidayTypeSpringRepository;

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
}
