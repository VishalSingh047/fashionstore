package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.AdminDashboardStatsResponse;
import com.fashionstore.fashionstore.dto.AdminOrderResponse;
import com.fashionstore.fashionstore.dto.OrderStatusDistribution;
import com.fashionstore.fashionstore.dto.SalesAnalyticsResponse;
import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.enums.OrderStatus;
import com.fashionstore.fashionstore.enums.Role;
import com.fashionstore.fashionstore.repository.OrderRepository;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.service.AdminDashboardService;
import com.fashionstore.fashionstore.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;
    private final OrderService orderService;

    public AdminDashboardServiceImpl(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserAccountRepository userAccountRepository,
            OrderService orderService
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
        this.orderService = orderService;
    }

    @Override
    public AdminDashboardStatsResponse getDashboardStats() {
        List<Order> orders = orderRepository.findAll();

        BigDecimal totalRevenue = orders.stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long totalOrders = orders.size();
        long pendingOrders = orders.stream().filter(o -> o.getStatus() == OrderStatus.PENDING).count();

        long totalProducts = productRepository.count();
        long outOfStockProducts = productRepository.findAll().stream()
                .filter(p -> Boolean.TRUE.equals(p.getSoldOut()) || p.getStock() == null || p.getStock() <= 0)
                .count();

        long totalCustomers = userAccountRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.CUSTOMER)
                .count();

        return new AdminDashboardStatsResponse(
                totalRevenue,
                totalOrders,
                pendingOrders,
                totalProducts,
                outOfStockProducts,
                totalCustomers
        );
    }

    @Override
    public SalesAnalyticsResponse getAnalytics() {
        AdminDashboardStatsResponse stats = getDashboardStats();

        List<Order> orders = orderRepository.findAll();
        Map<OrderStatus, Long> statusCounts = orders.stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));

        List<OrderStatusDistribution> breakdown = new ArrayList<>();
        for (OrderStatus status : OrderStatus.values()) {
            breakdown.add(new OrderStatusDistribution(status, statusCounts.getOrDefault(status, 0L)));
        }

        List<AdminOrderResponse> allOrders = orderService.getAllOrders();
        List<AdminOrderResponse> recentOrders = allOrders.stream()
                .sorted((a, b) -> b.getOrderedAt().compareTo(a.getOrderedAt()))
                .limit(10)
                .toList();

        return new SalesAnalyticsResponse(stats, breakdown, recentOrders);
    }
}
