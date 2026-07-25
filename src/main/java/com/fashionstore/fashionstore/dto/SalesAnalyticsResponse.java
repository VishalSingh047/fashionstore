package com.fashionstore.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesAnalyticsResponse {
    private AdminDashboardStatsResponse stats;
    private List<OrderStatusDistribution> orderStatusBreakdown;
    private List<AdminOrderResponse> recentOrders;
}
