package com.fashionstore.fashionstore.controller;


import com.fashionstore.fashionstore.dto.PaymentRequest;
import com.fashionstore.fashionstore.dto.PaymentResponse;
import com.fashionstore.fashionstore.dto.PaymentVerificationRequest;
import com.fashionstore.fashionstore.service.PaymentService;
import com.fashionstore.fashionstore.service.PaymentVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentVerificationService paymentVerificationService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponse> createOrder(
            @RequestBody PaymentRequest request
    ) {

        PaymentResponse response = paymentService.createOrder(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @RequestBody PaymentVerificationRequest request
    ) {
        paymentVerificationService.verifyPayment(request);

        return ResponseEntity.ok("Payment Verified Successfully");
    }

}
