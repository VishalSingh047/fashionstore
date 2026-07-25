package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.WishlistItemResponse;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.entity.WishlistItem;
import com.fashionstore.fashionstore.exception.DuplicateResourceException;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.repository.WishlistItemRepository;
import com.fashionstore.fashionstore.security.UserPrincipal;
import com.fashionstore.fashionstore.service.WishlistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistItemRepository wishlistRepository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public WishlistServiceImpl(
            WishlistItemRepository wishlistRepository,
            ProductRepository productRepository,
            UserAccountRepository userAccountRepository
    ) {
        this.wishlistRepository = wishlistRepository;
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
    public String addToWishlist(Long productId) {
        UserAccount user = getLoggedInUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

        if (wishlistRepository.existsByUserAndProduct(user, product)) {
            throw new DuplicateResourceException("Product is already in your wishlist.");
        }

        WishlistItem item = new WishlistItem();
        item.setUser(user);
        item.setProduct(product);

        wishlistRepository.save(item);
        return MessageConstants.PRODUCT_ADDED_TO_WISHLIST;
    }

    @Override
    @Transactional
    public String removeFromWishlist(Long productId) {
        UserAccount user = getLoggedInUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

        if (!wishlistRepository.existsByUserAndProduct(user, product)) {
            throw new ResourceNotFoundException("Product not found in wishlist.");
        }

        wishlistRepository.deleteByUserAndProduct(user, product);
        return MessageConstants.PRODUCT_REMOVED_FROM_WISHLIST;
    }

    @Override
    public List<WishlistItemResponse> getMyWishlist() {
        UserAccount user = getLoggedInUser();
        List<WishlistItem> items = wishlistRepository.findByUserOrderByCreatedAtDesc(user);

        return items.stream().map(item -> new WishlistItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getProductName(),
                item.getProduct().getCategory(),
                item.getProduct().getBrand(),
                item.getProduct().getPrice(),
                item.getProduct().getOriginalPrice(),
                item.getProduct().getDiscount(),
                item.getProduct().getImgUrl(),
                item.getProduct().getStock() != null && item.getProduct().getStock() > 0 && !Boolean.TRUE.equals(item.getProduct().getSoldOut()),
                item.getCreatedAt()
        )).toList();
    }

    @Override
    public boolean isWishlisted(Long productId) {
        UserAccount user = getLoggedInUser();
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return false;

        return wishlistRepository.existsByUserAndProduct(user, product);
    }

    @Override
    @Transactional
    public String clearWishlist() {
        UserAccount user = getLoggedInUser();
        wishlistRepository.deleteByUser(user);
        return MessageConstants.WISHLIST_CLEARED;
    }
}
