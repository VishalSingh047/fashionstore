package com.fashionstore.fashionstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class ProductRequest {
    @NotBlank(message = "Name is required")
    private String productName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Stock is required")
    private Integer stock;

}
