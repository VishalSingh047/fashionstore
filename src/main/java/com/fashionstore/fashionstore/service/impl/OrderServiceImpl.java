package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.AdminOrderResponse;
import com.fashionstore.fashionstore.dto.OrderItemResponse;
import com.fashionstore.fashionstore.dto.OrderResponse;
import com.fashionstore.fashionstore.dto.PlaceOrderRequest;
import com.fashionstore.fashionstore.entity.*;
import com.fashionstore.fashionstore.enums.OrderStatus;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.*;
import com.fashionstore.fashionstore.security.UserPrincipal;
import com.fashionstore.fashionstore.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserAccountRepository userAccountRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
    }

    private UserAccount getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        return userAccountRepository.findByEmail(principal.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request) {

        UserAccount user = getLoggedInUser();

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageConstants.CART_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        // Calculate total first
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            totalAmount = totalAmount.add(cartItem.getProduct().getPrice());
        }

        // Create Order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalAmount);

        order = orderRepository.save(order);

        List<OrderItemResponse> responses = new ArrayList<>();

        // Create Order Items
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            // Mark product unavailable
            product.setStock(0);
            product.setSoldOut(true);
            product.setActive(false);

            productRepository.save(product);

            responses.add(
                    new OrderItemResponse(
                            product.getId(),
                            product.getProductName(),
                            product.getImgUrl(),
                            product.getPrice()
                    )
            );
        }

        // Empty Cart
        cartItemRepository.deleteByCart(cart);

        return new OrderResponse(
                order.getId(),
                responses,
                totalAmount,
                order.getStatus(),
                order.getOrderedAt()
        );
    }

    @Override
    public List<OrderResponse> getMyOrders() {

        UserAccount user = getLoggedInUser();
        List<Order> orders = orderRepository.findByUserOrderByOrderedAtDesc(user);

        List<OrderResponse> responses = new ArrayList<>();

        for (Order order : orders) {
            List<OrderItem> orderItems =
                    orderItemRepository.findByOrder(order);

            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for (OrderItem item : orderItems) {
                itemResponses.add(
                        new OrderItemResponse(
                                item.getProduct().getId(),
                                item.getProduct().getProductName(),
                                item.getProduct().getImgUrl(),
                                item.getPrice()
                        ));
            }

            responses.add(
                    new OrderResponse(
                            order.getId(),
                            itemResponses,
                            order.getTotalAmount(),
                            order.getStatus(),
                            order.getOrderedAt()
                    ));
        }
        return responses;
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        UserAccount user = getLoggedInUser();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageConstants.ORDER_NOT_FOUND));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to view this order.");
        }

        List<OrderItem> orderItems =
                orderItemRepository.findByOrder(order);

        List<OrderItemResponse> itemResponses = new ArrayList<>();

        for (OrderItem item : orderItems) {
            itemResponses.add(
                    new OrderItemResponse(
                            item.getProduct().getId(),
                            item.getProduct().getProductName(),
                            item.getProduct().getImgUrl(),
                            item.getPrice()
                    ));
        }
        return new OrderResponse(
                order.getId(),
                itemResponses,
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderedAt()
        );
    }

    @Override
    public List<AdminOrderResponse> getAllOrders() {

        List<Order> orders = orderRepository.findAll();

        List<AdminOrderResponse> responses = new ArrayList<>();

        for (Order order : orders) {

            List<OrderItem> orderItems =
                    orderItemRepository.findByOrder(order);

            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for (OrderItem item : orderItems) {

                itemResponses.add(
                        new OrderItemResponse(
                                item.getProduct().getId(),
                                item.getProduct().getProductName(),
                                item.getProduct().getImgUrl(),
                                item.getPrice()
                        )
                );
            }

            responses.add(
                    new AdminOrderResponse(
                            order.getId(),

                            order.getUser().getId(),
                            order.getUser().getFullName(),
                            order.getUser().getEmail(),
                            order.getUser().getPhone(),

                            itemResponses,

                            order.getTotalAmount(),
                            order.getStatus(),
                            order.getOrderedAt()
                    )
            );
        }

        return responses;
    }

    @Override
    @Transactional
    public String updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageConstants.ORDER_NOT_FOUND));
        try {
            OrderStatus orderStatus =
                    OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(orderStatus);

            orderRepository.save(order);
            return MessageConstants.ORDER_STATUS_UPDATED;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(MessageConstants.INVALID_ORDER_STATUS);

        }
    }
}