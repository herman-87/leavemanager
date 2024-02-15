package com.app.leavemanager.api;

import com.app.leavemanager.dto.HolidayConfigDTO;
import com.app.leavemanager.service.HolidayConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HolidayConfigResources {

    private final HolidayConfigService holidayConfigService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/config/holiday")
    public ResponseEntity<Long> createHolidayConfig(@RequestBody HolidayConfigDTO holidayConfigDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayConfigService.create(holidayConfigDTO, getCurrentUsername()));
    }

    @GetMapping("/config/holiday")
    public ResponseEntity<List<HolidayConfigDTO>> getAllHolidayConfigs() {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayConfigService.getAllHolidayConfigs());
    }

    @GetMapping("/config/holiday/{holidayConfigId}")
    public ResponseEntity<HolidayConfigDTO> getHolidayConfigById(@PathVariable("holidayConfigId") Long holidayConfigId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayConfigService.getHolidayConfigById(holidayConfigId));
    }
}
