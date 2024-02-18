package com.app.leavemanager.api;

import com.app.leavemanager.service.HolidayService;
import com.leavemanager.openapi.api.HolidayApi;
import com.leavemanager.openapi.model.CreationHolidayDTO;
import com.leavemanager.openapi.model.HolidayDTO;
import com.leavemanager.openapi.model.HolidayTypeDTO;
import com.leavemanager.openapi.model.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HolidayResources implements HolidayApi {

    private final HolidayService holidayService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<Void> _approveHoliday(Long holidayId, NoticeDTO noticeDTO) {
        holidayService.approveHolidayById(holidayId, noticeDTO, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Long> _createHoliday(CreationHolidayDTO creationHolidayDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED).
                body(holidayService.createHoliday(creationHolidayDTO, getCurrentUsername()));
    }

    @Override
    public ResponseEntity<Long> _createHolidayType(HolidayTypeDTO holidayTypeDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayService.createHolidayType(
                        holidayTypeDTO,
                        getCurrentUsername()
                ));
    }

    @Override
    public ResponseEntity<Void> _deleteHolidayById(Long holidayId) {
        holidayService.deleteHolidayById(holidayId, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Void> _deleteHolidayTypeById(Long holidayTypeId) {
        holidayService.deleteHolidayTypeById(holidayTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<HolidayTypeDTO>> _getAllHolidayTypes() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayService.getAllHolidayTypes());
    }

    @Override
    public ResponseEntity<List<HolidayDTO>> _getAllHolidays() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayService.getAllHolidays());
    }

    @Override
    public ResponseEntity<HolidayDTO> _getHolidayById(Long holidayId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayService.getHolidayById(holidayId, getCurrentUsername()));
    }

    @Override
    public ResponseEntity<HolidayTypeDTO> _getHolidayTypeById(Long holidayTypeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayService.getHolidayTypeById(holidayTypeId));
    }

    @Override
    public ResponseEntity<Void> _publishHoliday(Long holidayId) {
        holidayService.publishHolidayById(holidayId, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _unapprovedHoliday(Long holidayId) {
        holidayService.unapprovedHolidayById(holidayId, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _unpublishedHoliday(Long holidayId) {
        holidayService.unpublishedHolidayById(holidayId, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _updateHoliday(Long holidayId, HolidayDTO holidayDTO) {
        holidayService.updateHoliday(holidayId, holidayDTO, getCurrentUsername());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Void> _updateHolidayType(Long holidayTypeId, HolidayTypeDTO holidayTypeDTO) {
        holidayService.updateHolidayTypeById(holidayTypeId, holidayTypeDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
