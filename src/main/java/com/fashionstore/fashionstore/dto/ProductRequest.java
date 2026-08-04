package com.fashionstore.fashionstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotBlank(message = "Product name is required")
    private String productName;

    private String description;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Image URL is required")
    private String imgUrl;

    private String brand;

    private BigDecimal originalPrice;

    private Integer discount;

    @NotNull(message = "Stock is required")
    private Integer stock = 1;

    private String size;

    private String imageGallery;

    private Boolean featured;

    private Boolean newArrival;


}