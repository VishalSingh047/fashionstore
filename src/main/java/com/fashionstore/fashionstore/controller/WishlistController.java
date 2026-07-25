package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.WishlistItemResponse;
import com.fashionstore.fashionstore.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@PreAuthorize("hasRole('CUSTOMER')")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> addToWishlist(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(wishlistService.addToWishlist(productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWishlist(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(wishlistService.removeFromWishlist(productId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistItemResponse>>> getMyWishlist() {
        return ResponseEntity.ok(ApiResponse.success(MessageConstants.WISHLIST_FETCHED, wishlistService.getMyWishlist()));
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<ApiResponse<Boolean>> isWishlisted(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success("Wishlist status checked", wishlistService.isWishlisted(productId)));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearWishlist() {
        return ResponseEntity.ok(ApiResponse.success(wishlistService.clearWishlist()));
    }
}
