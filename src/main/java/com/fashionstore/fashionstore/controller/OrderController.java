package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.AdminOrderResponse;
import com.fashionstore.fashionstore.dto.AdminPaymentResponse;
import com.fashionstore.fashionstore.dto.OrderResponse;
import com.fashionstore.fashionstore.dto.PlaceOrderRequest;
import com.fashionstore.fashionstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping("/test")
    public String test() {
        return "ORDER CONTROLLER WORKING";
    }

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ================= CUSTOMER =================

    @PostMapping("/place")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(
            @Valid @RequestBody PlaceOrderRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        MessageConstants.ORDER_PLACED,
                        orderService.placeOrder(request)
                )
        );
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        MessageConstants.ORDERS_FETCHED,
                        orderService.getMyOrders()
                )
        );
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        MessageConstants.ORDER_FETCHED,
                        orderService.getOrderById(orderId)
                )
        );
    }

    // ================= ADMIN =================

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdminOrderResponse>>> getAllOrders() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        MessageConstants.ORDERS_FETCHED,
                        orderService.getAllOrders()
                )
        );
    }

    @GetMapping("/admin/payments")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdminPaymentResponse>>> getAllPayments(){

        return ResponseEntity.ok(
                ApiResponse.success(
                        MessageConstants.ORDERS_FETCHED,
                        orderService.getAllPayments()
                )
        );
    }

    @PutMapping("/admin/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        orderService.updateOrderStatus(orderId, status)
                )
        );
    }

}