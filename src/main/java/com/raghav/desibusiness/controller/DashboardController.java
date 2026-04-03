package com.raghav.desibusiness.controller;


import com.raghav.desibusiness.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE','VIEWER')")
    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        return dashboardService.getDashboardSummary();
    }

    @PreAuthorize("hasAnyRole('OWNER','EMPLOYEE','VIEWER')")
    @GetMapping("/monthly-trends")
    public List<Map<String, Object>> getMonthlyTrends() {
        return dashboardService.getMonthlyTrends();
    }
}