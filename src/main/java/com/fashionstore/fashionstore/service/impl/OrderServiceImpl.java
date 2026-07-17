package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.enums.OrderStatus;
import com.fashionstore.fashionstore.repository.OrderRepository;
import com.fashionstore.fashionstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
