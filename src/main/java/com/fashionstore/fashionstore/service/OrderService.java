package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    String placeOrder(OrderRequest request);
}
