package com.raghav.desibusiness.controller;

import com.raghav.desibusiness.dto.FinancialRecordRequestDto;
import com.raghav.desibusiness.entity.FinancialRecord;
import com.raghav.desibusiness.entity.RecordType;
import com.raghav.desibusiness.service.FinancialRecordService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/records")
public class FinancialRecordController {

    private final FinancialRecordService recordService;

    public FinancialRecordController(FinancialRecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    public String createRecord(@Valid @RequestBody FinancialRecordRequestDto dto,
                                        Authentication authentication) {

        String email = authentication.getName();
        recordService.createRecord(dto, email);
        return "Record created successfully";
    }


    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE','VIEWER')")
    @GetMapping
    public List<FinancialRecord> getAllRecords() {
        return recordService.getAllRecords();
    }



    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public String updateRecord(@PathVariable Long id,
                                        @Valid @RequestBody FinancialRecordRequestDto dto) {
        recordService.updateRecord(id, dto);
        return "Record updated successfully";
    }


    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public String deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return "Record deleted successfully";
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE','VIEWER')")
    public List<FinancialRecord> filterRecords(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return recordService.filterRecords(category, type, startDate, endDate);
    }
}
