package com.fashionstore.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistItemResponse {
    private Long wishlistId;
    private Long productId;
    private String productName;
    private String category;
    private String brand;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer discount;
    private String imgUrl;
    private Boolean inStock;
    private LocalDateTime addedAt;
}
