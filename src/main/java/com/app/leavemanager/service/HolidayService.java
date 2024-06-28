package com.app.leavemanager.service;

import com.app.leavemanager.domain.employee.Employee;
import com.app.leavemanager.domain.employee.EmployeeRepository;
import com.app.leavemanager.domain.holiday.Holiday;
import com.app.leavemanager.domain.holiday.HolidayRepository;
import com.app.leavemanager.domain.holiday.HolidayStatus;
import com.app.leavemanager.domain.holiday.holidayType.HolidayType;
import com.app.leavemanager.domain.holiday.notice.Notice;
import com.app.leavemanager.mapper.HolidayMapper;
import com.leavemanager.openapi.model.CreationHolidayDTO;
import com.leavemanager.openapi.model.HolidayDTO;
import com.leavemanager.openapi.model.HolidayTypeDTO;
import com.leavemanager.openapi.model.NoticeDTO;
import com.leavemanager.openapi.model.ReasonDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;
    private final HolidayMapper holidayMapper;
    @Value("${scheduler.fixedRate}")
    private long fixedRate;

    private static boolean isAuthorOf(Employee employee, Holiday holiday) {
        return employee.hasRoleEmployee() && holiday.isCreatedBy(employee);
    }

    @Transactional
    public Long createHoliday(CreationHolidayDTO creationHolidayDTO, String currentUsername) {

        HolidayType holidayType = fetchHolidayTypeById(creationHolidayDTO.getType());
        Employee employee = getEmployeeByUsername(currentUsername);

        return employee.createHoliday(
                creationHolidayDTO.getTitle(),
                creationHolidayDTO.getDescription(),
                holidayMapper.toDTO(creationHolidayDTO.getPeriod()),
                holidayType,
                holidayRepository
        ).getId();
    }

    @Transactional
    public List<HolidayDTO> getAllHolidays() {
        return holidayRepository.findAllByStatusIsNot(HolidayStatus.DRAFT)
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }

    @Transactional
    public void updateHoliday(Long holidayId,
                              HolidayDTO holidayDTO,
                              String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        HolidayType holidayType = fetchHolidayTypeById(holidayDTO.getType().getId());

        if (isAuthorOf(employee, holiday)) {
            holiday.update(
                    holidayType,
                    holidayDTO.getDescription(),
                    holidayDTO.getTitle(),
                    holidayMapper.toDTO(holidayDTO.getPeriod()),
                    holidayRepository
            );
        } else {
            throw new RuntimeException("Forbidden for the current user");
        }
    }

    @Transactional
    public void deleteHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.deleteHoliday(holiday, holidayRepository);
        throw new RuntimeException("Forbidden for the current user");
    }

    @Transactional
    public HolidayDTO fetchHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);

        if (
                employee.hasRoleSuperAdmin()
                        || employee.hasRoleAdmin()
                        || isAuthorOf(employee, holiday)) {

            return holidayMapper.toDTO(holiday);
        }
        throw new RuntimeException("Forbidden for the current user");
    }

    public Holiday getHolidayById(Long holidayId) {
        return holidayRepository.findById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday Not Found"));
    }

    @Transactional
    public void noticeHolidayById(Long holidayId,
                                  NoticeDTO noticeDTO,
                                  String currentUsername) {
        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.noticeHoliday(
                holidayMapper.fromDTO(noticeDTO.getType()),
                noticeDTO.getDescription(),
                holiday,
                holidayRepository
        );
    }

    @Transactional
    public void publishHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.publishHoliday(holiday, holidayRepository);
    }

    @Transactional
    public void unpublishedHolidayById(Long holidayId, String currentUsername) {

        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.unpublishedHoliday(holiday, holidayRepository);
    }

    private Employee getEmployeeByUsername(String currentUsername) {
        return employeeRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new Error("the current user is not present in database"));
    }

    @Transactional
    public Long createHolidayType(HolidayTypeDTO holidayTypeDTO, String currentUsername) {

        Employee employee = getEmployeeByUsername(currentUsername);
        return employee.createHolidayType(
                holidayTypeDTO.getName(),
                holidayTypeDTO.getDescription(),
                holidayRepository
        ).getId();
    }

    @Transactional
    public List<HolidayTypeDTO> getAllHolidayTypes() {
        return holidayRepository.findAllHolidayTypes()
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }

    @Transactional
    public HolidayTypeDTO getHolidayTypeById(Long holidayId) {
        return holidayRepository.findHolidayTypeById(holidayId)
                .map(holidayMapper::toDTO)
                .orElseThrow();
    }

    @Transactional
    public void updateHolidayTypeById(Long holidayId, HolidayTypeDTO holidayTypeDTO) {

        HolidayType holidayType = fetchHolidayTypeById(holidayId);
        holidayType.update(
                holidayTypeDTO.getName(),
                holidayTypeDTO.getDescription(),
                holidayRepository
        );
    }

    private HolidayType fetchHolidayTypeById(Long holidayId) {
        return holidayRepository.findHolidayTypeById(holidayId)
                .orElseThrow(() -> new RuntimeException("Holiday type Not Found"));
    }

    @Transactional
    public void deleteHolidayTypeById(Long holidayTypeId) {

        HolidayType holidayType = fetchHolidayTypeById(holidayTypeId);
        holidayType.delete(holidayRepository);
    }

    @Transactional
    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    public void startScheduledTasks() {
        log.info("scheduler tour");

        holidayRepository.findAll()
                .forEach(holiday -> {
                    if (holiday.isValidated() && holiday.isStarted()) {
                        holiday.start(holidayRepository);
                        log.info("start : ".concat(holiday.getId().toString()));
                    } else if (holiday.isInProgress() && holiday.isPassed()){
                        holiday.close(holidayRepository);
                        log.info("close : ".concat(holiday.getId().toString()));

                    };
                });
    }

    @Transactional
    public List<NoticeDTO> getNoticesByHoliday(Long holidayId) {
        List<Notice> allNoticeByHolidayId = holidayRepository.findAllNoticeByHolidayId(holidayId);
        return allNoticeByHolidayId
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }

    @Transactional
    public void validateHoliday(Long holidayId, ReasonDTO reasonDTO, String currentUsername) {
        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.validateHoliday(holiday, reasonDTO.getValue(), holidayRepository);
    }

    @Transactional
    public void rejectHoliday(Long holidayId, ReasonDTO reasonDTO, String currentUsername) {
        Holiday holiday = getHolidayById(holidayId);
        Employee employee = getEmployeeByUsername(currentUsername);
        employee.rejectHoliday(holiday, reasonDTO.getValue(), holidayRepository);
    }

    @Transactional
    public List<HolidayDTO> getAllMyHolidays(String currentUsername) {
        Employee employee = getEmployeeByUsername(currentUsername);
        return holidayRepository.findAllByCreatedById(employee.getId())
                .stream()
                .map(holidayMapper::toDTO)
                .toList();
    }
}
