package com.fashionstore.fashionstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {
    @NotBlank
    private String customerName;

    @Email
    @NotBlank
    private String customerEmail;

    @NotBlank
    private String customerPhone;

    @NotBlank
    private String deliveryAddress;

    @NotBlank
    private String productName;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @Positive
    private BigDecimal totalAmount;
}
