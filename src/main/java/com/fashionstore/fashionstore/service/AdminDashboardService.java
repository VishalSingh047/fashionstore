package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.AdminDashboardStatsResponse;
import com.fashionstore.fashionstore.dto.SalesAnalyticsResponse;

public interface AdminDashboardService {
    AdminDashboardStatsResponse getDashboardStats();
    SalesAnalyticsResponse getAnalytics();
}
