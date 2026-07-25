package com.fashionstore.fashionstore.common;

public final class MessageConstants {

    private MessageConstants() {}

    // ================= USER =================

    public static final String USER_NOT_FOUND = "User not found.";
    public static final String LOGIN_SUCCESS = "Login successful.";
    public static final String REGISTER_SUCCESS = "Registration successful.";
    public static final String PASSWORD_CHANGED = "Password changed successfully.";
    public static final String PROFILE_UPDATED = "Profile updated successfully.";

    // ================= PRODUCT =================

    public static final String PRODUCT_NOT_FOUND = "Product not found.";
    public static final String PRODUCT_CREATED = "Product added successfully.";
    public static final String PRODUCT_UPDATED = "Product updated successfully.";
    public static final String PRODUCT_DELETED = "Product deleted successfully.";
    public static final String PRODUCT_SOLD_OUT = "Product is sold out.";
    public static final String PRODUCT_OUT_OF_STOCK = "Product is out of stock.";

    // ================= CART =================

    public static final String CART_NOT_FOUND = "Cart not found.";
    public static final String PRODUCT_ADDED_TO_CART = "Product added to cart successfully.";
    public static final String PRODUCT_ALREADY_IN_CART = "Product already added to cart.";
    public static final String PRODUCT_REMOVED_FROM_CART = "Product removed successfully.";
    public static final String PRODUCT_NOT_FOUND_IN_CART = "Product not found in cart.";
    public static final String CART_CLEARED = "Cart cleared successfully.";

    // ================= ORDER =================

    public static final String ORDER_PLACED = "Order placed successfully.";
    public static final String ORDER_NOT_FOUND = "Order not found.";
    public static final String ORDER_CANCELLED = "Order cancelled successfully.";
    public static final String ORDER_STATUS_UPDATED = "Order status updated successfully.";
    public static final String INVALID_ORDER_STATUS = "Invalid order status.";
    public static final String ORDER_FETCHED = "Order fetched successfully.";
    public static final String ORDERS_FETCHED = "Orders fetched successfully.";

    // ================= WISHLIST =================

    public static final String PRODUCT_ADDED_TO_WISHLIST = "Product added to wishlist successfully.";
    public static final String PRODUCT_REMOVED_FROM_WISHLIST = "Product removed from wishlist successfully.";
    public static final String WISHLIST_CLEARED = "Wishlist cleared successfully.";
    public static final String WISHLIST_FETCHED = "Wishlist fetched successfully.";

    // ================= REVIEWS =================

    public static final String REVIEW_ADDED = "Review submitted successfully.";
    public static final String REVIEW_DELETED = "Review deleted successfully.";
    public static final String REVIEW_FETCHED = "Reviews fetched successfully.";
    public static final String REVIEW_ALREADY_EXISTS = "You have already reviewed this product.";
    public static final String REVIEW_NOT_FOUND = "Review not found.";

    // ================= GENERAL =================

    public static final String UNAUTHORIZED = "Unauthorized access.";
    public static final String ACCESS_DENIED = "Access denied.";
    public static final String INTERNAL_SERVER_ERROR = "Something went wrong.";
}