package com.app.leavemanager.api;

import com.app.leavemanager.dto.HolidayConfigDTO;
import com.app.leavemanager.service.HolidayConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HolidayConfigResources {

    private final HolidayConfigService holidayConfigService;

    @PostMapping("/config/holiday")
    public ResponseEntity<Long> createHolidayConfig(@RequestBody HolidayConfigDTO holidayConfigDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayConfigService.create(holidayConfigDTO, getCurrentUsername()));
    }

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
