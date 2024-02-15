package com.app.leavemanager.api;

import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.dto.HolidayTypeDTO;
import com.app.leavemanager.service.HolidayService;
import com.leavemanager.openapi.api.HolidayApi;
import com.leavemanager.openapi.model.CreationHolidayDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HolidayResources implements HolidayApi {

    private final HolidayService holidayService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


//
//    @GetMapping("/holiday/{holidayId}")
//    public ResponseEntity<HolidayDTO> getHolidayById(@PathVariable("holidayId") Long holidayId) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(holidayService.getHolidayById(holidayId, getCurrentUsername()));
//    }
//
//    @PutMapping("/holiday/{holidayId}")
//    public ResponseEntity<Void> updateHoliday(@PathVariable("holidayId") Long holidayId,
//                                              @RequestBody(required = true) HolidayDTO holidayDTO) {
//        holidayService.updateHoliday(holidayId, holidayDTO, getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @DeleteMapping("/holiday/{holidayId}")
//    public ResponseEntity<Void> deleteHoliday(@PathVariable("holidayId") Long holidayId) {
//        holidayService.deleteHolidayById(holidayId, getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
//    }
//
//    @PutMapping("/approve/{holidayId}")
//    public ResponseEntity<Void> approveHolidayById(@PathVariable("holidayId") Long holidayId) {
//        holidayService.approveHolidayById(holidayId, getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @PutMapping("/publish/{holidayId}")
//    public ResponseEntity<Void> publishHolidayById(@PathVariable("holidayId") Long holidayId) {
//        holidayService.publishHolidayById(holidayId, getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @PutMapping("/unapproved/{holidayId}")
//    public ResponseEntity<Void> unapprovedHolidayById(@PathVariable("holidayId") Long holidayId) {
//        holidayService.unapprovedHolidayById(holidayId, getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @PutMapping("/unpublished/{holidayId}")
//    public ResponseEntity<Void> unpublishedHolidayById(@PathVariable("holidayId") Long holidayId) {
//        holidayService.unpublishedHolidayById(holidayId, getCurrentUsername());
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @PostMapping("/holiday/type")
//    public ResponseEntity<Long> createHolidayType(@RequestBody HolidayTypeDTO holidayTypeDTO) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(holidayService.createHolidayType(
//                                holidayTypeDTO,
//                                getCurrentUsername()
//                ));
//    }
//
//    @GetMapping("/holiday/type")
//    public ResponseEntity<List<HolidayTypeDTO>> getALlHolidayType() {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(holidayService.getAllHolidayTypes());
//    }
//
//    @GetMapping("/holiday/type/{holidayTypeId}")
//    public ResponseEntity<HolidayTypeDTO> getHolidayTypeById(@PathVariable("holidayTypeId") Long holidayId) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(holidayService.getHolidayTypeById(holidayId));
//    }
//
//    @PutMapping("/holiday/type/{holidayTypeId}")
//    public ResponseEntity<Void> updateHolidayType(@PathVariable("holidayTypeId") Long holidayId,
//                                                  @RequestBody HolidayTypeDTO holidayTypeDTO) {
//        holidayService.updateHolidayTypeById(holidayId, holidayTypeDTO);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }
//
//    @DeleteMapping("/holiday/type/{holidayTypeId}")
//    public ResponseEntity<Void> deleteHolidayType(@PathVariable("holidayTypeId") Long holidayTypeId) {
//
//        holidayService.deleteHolidayTypeById(holidayTypeId);
//        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//    }

    @Override
    public ResponseEntity<Void> _approveHoliday(Long holidayId) {
        return null;
    }

    @Override
    public ResponseEntity<Long> _createHoliday(CreationHolidayDTO creationHolidayDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED).
                body(holidayService.createHoliday(creationHolidayDTO, getCurrentUsername()));
    }

    @Override
    public ResponseEntity<Long> _createHolidayType(com.leavemanager.openapi.model.HolidayTypeDTO holidayTypeDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteHolidayById(Long holidayId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteHolidayTypeById(Long holidayTypeId) {
        return null;
    }

    @Override
    public ResponseEntity<List<com.leavemanager.openapi.model.HolidayTypeDTO>> _getAllHolidayTypes() {
        return null;
    }

    @Override
    public ResponseEntity<List<HolidayDTO>> _getAllHolidays() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayService.getAllHolidays(getCurrentUsername()));
    }

    @Override
    public ResponseEntity<com.leavemanager.openapi.model.HolidayDTO> _getHolidayById(Long holidayId) {
        return null;
    }

    @Override
    public ResponseEntity<com.leavemanager.openapi.model.HolidayDTO> _getHolidayTypeById(Long holidayTypeId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _publishHoliday(Long holidayId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _unapprovedHoliday(Long holidayId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _unpublishedHoliday(Long holidayId) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _updateHoliday(Long holidayId, com.leavemanager.openapi.model.HolidayDTO holidayDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Void> _updateHolidayType(Long holidayTypeId, com.leavemanager.openapi.model.HolidayTypeDTO holidayTypeDTO) {
        return null;
    }
}
