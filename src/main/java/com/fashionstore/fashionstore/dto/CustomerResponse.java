package com.fashionstore.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private Long totalOrders;

    private BigDecimal totalSpent;

    private LocalDateTime joinedAt;
}