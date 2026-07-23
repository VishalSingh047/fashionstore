package com.fashionstore.fashionstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Delivery Address is required")
    private String deliveryAddress;

    @Getter
    @Setter
    public static class ProductRequest {


        // Basic Information

        @NotBlank(message = "Product name is required")
        private String productName;


        private String description;


        @NotBlank(message = "Category is required")
        private String category;


        private String brand;



        // Pricing

        @NotNull(message = "Price is required")
        private BigDecimal price;


        private BigDecimal originalPrice;


        private Integer discount;



        // Single Piece Stock

        @NotNull(message = "Stock is required")
        private Integer stock;



        // Size

        private String size;



        // Images

        @NotBlank(message = "Image URL is required")
        private String imgUrl;


        private String imageGallery;



        // Homepage Controls

        private Boolean featured = false;


        private Boolean newArrival = true;

    }
}