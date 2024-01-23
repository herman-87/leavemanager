package com.app.leavemanager.controller;

import com.app.leavemanager.DTO.HolidayDTO;
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
public class HolidayController {

    private final HolidayService holidayService;

    @PostMapping
    public ResponseEntity<Integer> createHoliday(@RequestBody(required = true) HolidayDTO holidayDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(holidayService.createHoliday(holidayDTO));
    }

    @GetMapping
    public ResponseEntity<List<HolidayDTO>> getAllHoliday() {
        return ResponseEntity.status(HttpStatus.CREATED).body(holidayService.getAllHoliday());
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
}
