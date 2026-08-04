package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.AddToCartRequest;
import com.fashionstore.fashionstore.dto.CartResponse;
import com.fashionstore.fashionstore.entity.Cart;
import com.fashionstore.fashionstore.entity.CartItem;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.exception.DuplicateResourceException;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.CartItemRepository;
import com.fashionstore.fashionstore.repository.CartRepository;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.fashionstore.fashionstore.dto.CartItemResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * Returns the currently logged-in user
     */
    private UserAccount getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userAccountRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND));
    }

    @Override
    public String addToCart(AddToCartRequest request) {
        UserAccount user = getLoggedInUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> { Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

        if (Boolean.TRUE.equals(product.getSoldOut())) {
            throw new RuntimeException(MessageConstants.PRODUCT_SOLD_OUT);
        }


//        if (product.getStock() < request.getQuantity()) {
//            throw new RuntimeException("Not enough stock available");
//        }

        if (product.getStock() <= 0) {
            throw new RuntimeException(MessageConstants.PRODUCT_OUT_OF_STOCK);
        }

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product).orElse(null);


//        if (cartItem != null) {
//            int updatedQuantity = cartItem.getQuantity() + request.getQuantity();
//            if (updatedQuantity > product.getStock()) {
//                throw new RuntimeException(
//                        "Requested quantity exceeds available stock"
//                );
//            }
//            cartItem.setQuantity(updatedQuantity);
//        } else {
//            cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(request.getQuantity());
//
//            return "Product added to cart successfully";
//        }

        if (cartItem != null) {
            throw new DuplicateResourceException(MessageConstants.PRODUCT_ALREADY_IN_CART);
        }
        cartItem = new CartItem();

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(1);

        cartItemRepository.save(cartItem);
        return MessageConstants.PRODUCT_ADDED_TO_CART;
    }

    @Override
    public CartResponse getMyCart() {
        UserAccount user = getLoggedInUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> { Cart newCart = new Cart();
                    newCart.setUser(user);

                    return cartRepository.save(newCart);
                });

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        List<CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        int totalItems = 0;

        for (CartItem item : cartItems) {

            BigDecimal subTotal = item.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            totalAmount = totalAmount.add(subTotal);
            totalItems += item.getQuantity();

            itemResponses.add(
                    new CartItemResponse(
                            item.getProduct().getId(),
                            item.getProduct().getProductName(),
                            item.getProduct().getPrice(),
                            item.getProduct().getImgUrl(),
                            item.getQuantity(),
                            subTotal
                    )
            );
        }

        return new CartResponse(
                itemResponses,
                totalItems,
                totalAmount
        );
    }

    @Override
    public String removeItem(Long productId) {
        UserAccount user = getLoggedInUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.CART_NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND_IN_CART));

        cartItemRepository.delete(cartItem);

        return MessageConstants.PRODUCT_REMOVED_FROM_CART;
    }

    @Transactional
    @Override
    public String clearCart() {

        UserAccount user = getLoggedInUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cartItemRepository.deleteByCart(cart);

        return MessageConstants.CART_CLEARED;
    }
}