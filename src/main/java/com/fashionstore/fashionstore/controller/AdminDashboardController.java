package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.dto.AdminDashboardStatsResponse;
import com.fashionstore.fashionstore.dto.SalesAnalyticsResponse;
import com.fashionstore.fashionstore.service.AdminDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(AdminDashboardService adminDashboardService) {
        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardStatsResponse>> getDashboardStats() {
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats fetched", adminDashboardService.getDashboardStats()));
    }

    @GetMapping("/analytics")
    public ResponseEntity<ApiResponse<SalesAnalyticsResponse>> getAnalytics() {
        return ResponseEntity.ok(ApiResponse.success("Sales analytics fetched", adminDashboardService.getAnalytics()));
    }
}
