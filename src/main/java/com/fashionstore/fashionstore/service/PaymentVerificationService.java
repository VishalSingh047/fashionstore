package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.PaymentVerificationRequest;

public interface PaymentVerificationService {

    boolean verifyPayment(PaymentVerificationRequest request);

}