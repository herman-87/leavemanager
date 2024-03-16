package com.app.leavemanager.api;

import com.app.leavemanager.service.HolidayConfigService;
import com.leavemanager.openapi.api.HolidayConfigApi;
import com.leavemanager.openapi.model.HolidayConfigDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HolidayConfigResources implements HolidayConfigApi {

    private final HolidayConfigService holidayConfigService;

    private static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public ResponseEntity<Void> _activateHolidayConfig(Long holidayConfigId) {
        holidayConfigService.activateHolidayById(holidayConfigId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Long> _createHolidayConfig(HolidayConfigDTO holidayConfigDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayConfigService.create(holidayConfigDTO, getCurrentUsername()));
    }

    @Override
    public ResponseEntity<Void> _deactivateHolidayConfig(Long holidayConfigId) {
        holidayConfigService.deactivateHolidayById(holidayConfigId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<HolidayConfigDTO> _getActivatedHolidayConfigByHolidayTypeId(Long holidayTypeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayConfigService.getActivatedHolidayConfigsByHolidayType(holidayTypeId));
    }

    @Override
    public ResponseEntity<List<HolidayConfigDTO>> _getAllHolidayConfigByHolidayType(Long holidayTypeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayConfigService.getAllHolidayConfigsByHolidayType(holidayTypeId));
    }

    @Override
    public ResponseEntity<List<HolidayConfigDTO>> _getAllHolidayConfigs() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayConfigService.getAllHolidayConfigs());
    }

    @Override
    public ResponseEntity<HolidayConfigDTO> _getHolidayConfigById(Long holidayConfigId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(holidayConfigService.getHolidayConfigById(holidayConfigId));
    }
}
