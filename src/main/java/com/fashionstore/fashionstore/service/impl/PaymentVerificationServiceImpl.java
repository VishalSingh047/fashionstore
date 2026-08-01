package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.PaymentVerificationRequest;
import com.fashionstore.fashionstore.entity.Payment;
import com.fashionstore.fashionstore.enums.PaymentStatus;
import com.fashionstore.fashionstore.service.PaymentRecordService;
import com.fashionstore.fashionstore.service.PaymentVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentVerificationServiceImpl implements PaymentVerificationService {

    @Autowired
    private PaymentRecordService paymentRecordService;

    @Override
    public boolean verifyPayment(PaymentVerificationRequest request) {

        Payment payment = new Payment();

        payment.setRazorpayOrderId(request.getRazorpayOrderId());
        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
        payment.setRazorpaySignature(request.getRazorpaySignature());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());

        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentDate(LocalDateTime.now());

        paymentRecordService.savePayment(payment);

        return true;
    }
}