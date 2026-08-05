package com.fashionstore.fashionstore.dto;

import com.fashionstore.fashionstore.enums.PaymentMethod;
import com.fashionstore.fashionstore.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class AdminPaymentResponse {

    private Long orderId;

    private String customerName;

    private String customerEmail;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate;

}
