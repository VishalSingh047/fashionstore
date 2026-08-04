package com.fashionstore.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponse {

    private Long id;
    private String productName;
    private BigDecimal price;
    private String imgUrl;
    private Integer quantity;
    private BigDecimal subTotal;
}