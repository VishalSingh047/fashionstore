package com.fashionstore.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStatsResponse {
    private BigDecimal totalRevenue;
    private long totalOrders;
    private long pendingOrders;
    private long totalProducts;
    private long outOfStockProducts;
    private long totalCustomers;
}
