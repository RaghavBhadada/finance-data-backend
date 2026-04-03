package com.raghav.desibusiness.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
}
