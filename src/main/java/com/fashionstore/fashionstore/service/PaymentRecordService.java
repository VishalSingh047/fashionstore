package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.entity.Payment;

public interface PaymentRecordService {

    Payment savePayment(Payment payment);

    Payment getByOrderId(String razorpayOrderId);

}