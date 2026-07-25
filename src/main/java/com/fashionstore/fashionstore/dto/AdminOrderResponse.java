package com.fashionstore.fashionstore.dto;

import com.fashionstore.fashionstore.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AdminOrderResponse {

    private Long orderId;

    // Customer Details
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    // Order Details
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderedAt;
}