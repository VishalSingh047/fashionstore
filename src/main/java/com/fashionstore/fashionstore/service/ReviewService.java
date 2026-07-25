package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.CreateReviewRequest;
import com.fashionstore.fashionstore.dto.PageResponse;
import com.fashionstore.fashionstore.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {
    String createReview(CreateReviewRequest request);
    PageResponse<ReviewResponse> getProductReviews(Long productId, int page, int size);
    List<ReviewResponse> getMyReviews();
    String deleteReview(Long reviewId);
}
