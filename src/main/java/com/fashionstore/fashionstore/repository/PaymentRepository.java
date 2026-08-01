package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByRazorpayOrderId(String razorpayOrderId);

    @Query("""
       SELECT SUM(p.amount)
       FROM Payment p
       WHERE p.paymentStatus = com.fashionstore.fashionstore.enums.PaymentStatus.SUCCESS
       """)
    Double getTotalRevenue();
}