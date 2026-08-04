package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.dto.AdminOrderResponse;
import com.fashionstore.fashionstore.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminOrderResponse>>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Orders fetched",
                        orderService.getAllOrders(pageable)
                )
        );
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<AdminOrderResponse>>> getAllOrders() {
//
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        "Orders fetched successfully",
//                        orderService.getAllOrders()
//                )
//        );
//    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<String>> updateStatus(
            @PathVariable Long orderId,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        orderService.updateOrderStatus(orderId, status)
                )
        );
    }
}