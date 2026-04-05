package com.raghav.desibusiness.controller;

import com.raghav.desibusiness.dto.EmployeeRequestDto;

import com.raghav.desibusiness.entity.Role;
import com.raghav.desibusiness.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    private final UserService userService;


    public BusinessController(UserService userService) {
        this.userService = userService;

    }



    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/add-employee")
    public String addEmployee(@Valid @RequestBody EmployeeRequestDto request) {
        return userService.createEmployee(request , Role.EMPLOYEE);
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE')")
    @PostMapping("/add-viewer")
    public String addViewer(@Valid @RequestBody EmployeeRequestDto request) {
        return userService.createEmployee(request, Role.VIEWER);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/users/{id}/status")
    public String updateUserStatus(@PathVariable Long id) {
        return userService.updateUserStatus(id);
    }


}
