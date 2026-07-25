package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.CreateReviewRequest;
import com.fashionstore.fashionstore.dto.PageResponse;
import com.fashionstore.fashionstore.dto.ReviewResponse;
import com.fashionstore.fashionstore.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<String>> createReview(@Valid @RequestBody CreateReviewRequest request) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.createReview(request)));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<PageResponse<ReviewResponse>>> getProductReviews(
            @PathVariable Long productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                MessageConstants.REVIEW_FETCHED,
                reviewService.getProductReviews(productId, page, size)
        ));
    }

    @GetMapping("/my-reviews")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getMyReviews() {
        return ResponseEntity.ok(ApiResponse.success(MessageConstants.REVIEW_FETCHED, reviewService.getMyReviews()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteReview(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.deleteReview(id)));
    }
}
