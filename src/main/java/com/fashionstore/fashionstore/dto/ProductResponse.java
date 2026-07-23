package com.fashionstore.fashionstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private String productName;

    private String description;

    private String category;

    private String brand;


    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer discount;


    private Integer stock;

    private Boolean soldOut;


    private String size;


    private String imgUrl;

    private String imageGallery;


    private Double rating;

    private Integer reviewCount;


    private Boolean featured;

    private Boolean newArrival;


    private LocalDateTime createdAt;
}