package com.raghav.desibusiness.dto;


import com.raghav.desibusiness.entity.RecordType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FinancialRecordRequestDto {

    @NotNull
    private Double amount;

    @NotNull
    private RecordType type;

    @NotNull
    private String category;

    @NotNull
    private LocalDate date;

    private String description;
}
