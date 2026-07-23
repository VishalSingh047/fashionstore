package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.OrderRequest;
import com.fashionstore.fashionstore.dto.UpdateOrderStatusRequest;
import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.enums.OrderStatus;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.OrderRepository;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public String placeOrder(OrderRequest request) {

        // Get Logged-in User Email from JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Fetch Logged-in User
        UserAccount user = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        // Fetch Product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        // Calculate Total Amount
        BigDecimal totalAmount = product.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        // Create Order
        Order order = new Order();

        order.setCustomerName(user.getFullName());
        order.setCustomerEmail(user.getEmail());
        order.setCustomerPhone(user.getPhone());

        order.setDeliveryAddress(request.getDeliveryAddress());

        order.setProductId(product.getId());
        order.setProductName(product.getProductName());
        order.setPrice(product.getPrice());

        order.setQuantity(request.getQuantity());
        order.setTotalAmount(totalAmount);

        order.setOrderStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        return "Order placed successfully";
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));
    }

    @Override
    public String updateOrderStatus(Long id, UpdateOrderStatusRequest request) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order Not Found"));

        order.setOrderStatus(request.getOrderStatus());

        orderRepository.save(order);

        return "Order status updated successfully";
    }

    @Override
    public List<Order> getMyOrders() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return orderRepository.findByCustomerEmail(email);
    }
}