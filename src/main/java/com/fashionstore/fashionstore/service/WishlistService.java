package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.WishlistItemResponse;

import java.util.List;

public interface WishlistService {
    String addToWishlist(Long productId);
    String removeFromWishlist(Long productId);
    List<WishlistItemResponse> getMyWishlist();
    boolean isWishlisted(Long productId);
    String clearWishlist();
}
