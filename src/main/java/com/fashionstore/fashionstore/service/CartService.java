package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.AddToCartRequest;
import com.fashionstore.fashionstore.dto.CartResponse;

public interface CartService {

    String addToCart(AddToCartRequest request);

    CartResponse getMyCart();

    String removeItem(Long productId);

    String clearCart();
}