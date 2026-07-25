package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.dto.AddToCartRequest;
import com.fashionstore.fashionstore.dto.CartResponse;
import com.fashionstore.fashionstore.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> addToCart(
            @Valid @RequestBody AddToCartRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.success(cartService.addToCart(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getMyCart()
    {
        return ResponseEntity.ok(ApiResponse.success("Cart fetched successfully", cartService.getMyCart()));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(ApiResponse.success(cartService.removeItem(productId)));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart() {
        return ResponseEntity.ok(ApiResponse.success(cartService.clearCart()));
    }
}