package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.AdminOrderResponse;
import com.fashionstore.fashionstore.dto.OrderResponse;
import com.fashionstore.fashionstore.dto.PlaceOrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    // Customer
    OrderResponse placeOrder(PlaceOrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);

    // Admin
    List<AdminOrderResponse> getAllOrders();

    // Admin DashBoard
    Page<AdminOrderResponse> getAllOrders(Pageable pageable);

    String updateOrderStatus(Long orderId, String status);


}