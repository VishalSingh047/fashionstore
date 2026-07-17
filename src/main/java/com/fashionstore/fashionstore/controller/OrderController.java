package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public String placeOrder(@Valid @RequestBody OrderRequest request){
        return orderService.placeOrder(request);
    }
}
