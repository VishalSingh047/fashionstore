package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.dto.UpdateOrderStatusRequest;
import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public String placeOrder(@Valid @RequestBody OrderRequest request){
        return orderService.placeOrder(request);
    }

    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }

    @PatchMapping("/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @Valid @RequestBody UpdateOrderStatusRequest request){
        return orderService.updateOrderStatus(id, request);
    }
}
