package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.AdminOrderResponse;
import com.fashionstore.fashionstore.dto.OrderResponse;
import com.fashionstore.fashionstore.dto.PlaceOrderRequest;

import java.util.List;

public interface OrderService {

    // Customer
    OrderResponse placeOrder(PlaceOrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long orderId);

    // Admin
    List<AdminOrderResponse> getAllOrders();


    String updateOrderStatus(Long orderId, String status);

}