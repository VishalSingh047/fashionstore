package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.PaymentRequest;
import com.fashionstore.fashionstore.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createOrder(PaymentRequest request);

}