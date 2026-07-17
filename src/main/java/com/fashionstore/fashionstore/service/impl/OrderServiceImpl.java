package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.dto.UpdateOrderStatusRequest;
import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.enums.OrderStatus;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.OrderRepository;
import com.fashionstore.fashionstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public String placeOrder(OrderRequest request){
        Order order = new Order();

        order.setCustomerName(request.getCustomerName());
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setProductName(request.getProductName());
        order.setQuantity(request.getQuantity());
        order.setTotalAmount(request.getTotalAmount());

        order.setOrderStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        return "Order placed successfully";
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));
    }

    @Override
    public String updateOrderStatus(Long id, UpdateOrderStatusRequest request){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));

        order.setOrderStatus(request.getOrderStatus());

        orderRepository.save(order);
        return "Order status updated successfully";
    }
}
