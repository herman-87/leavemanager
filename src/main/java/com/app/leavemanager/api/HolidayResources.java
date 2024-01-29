package com.app.leavemanager.api;

import com.app.leavemanager.dto.HolidayDTO;
import com.app.leavemanager.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/holiday")
public class HolidayResources {

    private final HolidayService holidayService;

    @PostMapping
    public ResponseEntity<Integer> createHoliday(@RequestBody(required = true) HolidayDTO holidayDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(holidayService.createHoliday(holidayDTO));
    }

    @GetMapping
    public ResponseEntity<List<HolidayDTO>> getAllHoliday() {
        return ResponseEntity.status(HttpStatus.CREATED).body(holidayService.getAllHoliday());
    }

    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayDTO> getHolidayById(@PathVariable("holidayId") Integer holidayId) {
        return ResponseEntity.status(HttpStatus.OK).body(holidayService.getHolidayById(holidayId));
    }

    @PutMapping("/{holidayId}")
    public ResponseEntity<Void> updateHoliday(@PathVariable("holidayId") Integer holidayId,
                                              @RequestBody(required = true) HolidayDTO holidayDTO) {

        holidayService.updateHoliday(holidayId, holidayDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{holidayId}")
    public ResponseEntity<Void> deleteHoliday(@PathVariable("holidayId") Integer holidayId) {

        holidayService.deleteHoliday(holidayId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/approve/{holidayId}")
    public ResponseEntity<Void> approveHoliday(@PathVariable("holidayId") Integer holidayId) {
        holidayService.approveHoliday(holidayId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/publish/{holidayId}")
    public ResponseEntity<Void> publishHoliday(@PathVariable("holidayId") Integer holidayId) {
        holidayService.publishHoliday(holidayId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/unapproved/{holidayId}")
    public ResponseEntity<Void> unapprovedHoliday(@PathVariable("holidayId") Integer holidayId) {
        holidayService.unapprovedHoliday(holidayId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/unpublished/{holidayId}")
    public ResponseEntity<Void> unpublishedHoliday(@PathVariable("holidayId") Integer holidayId) {
        holidayService.unpublishedHoliday(holidayId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}