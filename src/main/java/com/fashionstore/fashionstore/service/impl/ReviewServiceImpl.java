package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.CreateReviewRequest;
import com.fashionstore.fashionstore.dto.PageResponse;
import com.fashionstore.fashionstore.dto.ReviewResponse;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.entity.Review;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.enums.Role;
import com.fashionstore.fashionstore.exception.DuplicateResourceException;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.repository.ReviewRepository;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.security.UserPrincipal;
import com.fashionstore.fashionstore.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            ProductRepository productRepository,
            UserAccountRepository userAccountRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
    }

    private UserAccount getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND);
        }

        return userAccountRepository.findByEmail(principal.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public String createReview(CreateReviewRequest request) {
        UserAccount user = getLoggedInUser();

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

        if (reviewRepository.existsByUserAndProduct(user, product)) {
            throw new DuplicateResourceException(MessageConstants.REVIEW_ALREADY_EXISTS);
        }

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        reviewRepository.save(review);

        // Dynamic Product Rating Update
        updateProductRatingStats(product);

        return MessageConstants.REVIEW_ADDED;
    }

    @Override
    public PageResponse<ReviewResponse> getProductReviews(Long productId, int page, int size) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewRepository.findByProductOrderByCreatedAtDesc(product, pageable);

        List<ReviewResponse> content = reviewPage.getContent().stream()
                .map(r -> new ReviewResponse(
                        r.getId(),
                        product.getId(),
                        r.getUser().getFullName(),
                        r.getRating(),
                        r.getComment(),
                        r.getCreatedAt()
                )).toList();

        return new PageResponse<>(
                content,
                reviewPage.getNumber(),
                reviewPage.getSize(),
                reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.isLast()
        );
    }

    @Override
    public List<ReviewResponse> getMyReviews() {
        UserAccount user = getLoggedInUser();
        List<Review> reviews = reviewRepository.findByUserOrderByCreatedAtDesc(user);

        return reviews.stream().map(r -> new ReviewResponse(
                r.getId(),
                r.getProduct().getId(),
                r.getUser().getFullName(),
                r.getRating(),
                r.getComment(),
                r.getCreatedAt()
        )).toList();
    }

    @Override
    @Transactional
    public String deleteReview(Long reviewId) {
        UserAccount user = getLoggedInUser();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.REVIEW_NOT_FOUND));

        // Allow deletion if user owns the review OR user is admin
        if (!review.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new RuntimeException("You are not authorized to delete this review.");
        }

        Product product = review.getProduct();
        reviewRepository.delete(review);

        // Dynamic Product Rating Update
        updateProductRatingStats(product);

        return MessageConstants.REVIEW_DELETED;
    }

    private void updateProductRatingStats(Product product) {
        Double avgRating = reviewRepository.calculateAverageRating(product);
        long count = reviewRepository.countByProduct(product);

        product.setRating(avgRating != null ? Math.round(avgRating * 10.0) / 10.0 : 0.0);
        product.setReviewCount((int) count);

        productRepository.save(product);
    }
}
