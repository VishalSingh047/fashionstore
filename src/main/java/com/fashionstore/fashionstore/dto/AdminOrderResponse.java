package com.fashionstore.fashionstore.dto;

import com.fashionstore.fashionstore.enums.OrderStatus;
import com.fashionstore.fashionstore.enums.PaymentMethod;
import com.fashionstore.fashionstore.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderResponse {

    private Long orderId;

    // Customer Details
    private Long customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    // Shipping Details
    private String shippingFullName;
    private String shippingPhone;
    private String shippingAddress;
    private String city;
    private String state;
    private String pincode;

    // Payment Details
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;

    // Order Details
    private List<OrderItemResponse> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderedAt;
}