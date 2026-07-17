package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.dto.UpdateOrderStatusRequest;
import com.fashionstore.fashionstore.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    String placeOrder(OrderRequest request);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    String updateOrderStatus(Long id, UpdateOrderStatusRequest request);
}
