package com.fashionstore.fashionstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {
    private String productName;
    private String description;
    private String category;
    private String brand;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer discount;
    private Integer stock;
    private String size;
    private String imgUrl;

    // Entity me String hai, List<String> nahi
    private String imageGallery;
    private Boolean featured;
    private Boolean newArrival;
}