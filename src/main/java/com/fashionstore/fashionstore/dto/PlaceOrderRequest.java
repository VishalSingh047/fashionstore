package com.fashionstore.fashionstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceOrderRequest {

    // Customer shipping details
    private String fullName;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String pincode;

    // Future:
    // COD / ONLINE
    private String paymentMethod;
}