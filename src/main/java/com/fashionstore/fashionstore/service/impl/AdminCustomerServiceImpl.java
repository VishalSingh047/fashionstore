package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.CustomerResponse;
import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.enums.Role;
import com.fashionstore.fashionstore.repository.OrderRepository;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.service.AdminCustomerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCustomerServiceImpl implements AdminCustomerService {


    private final UserAccountRepository userAccountRepository;
    private final OrderRepository orderRepository;


    public AdminCustomerServiceImpl(
            UserAccountRepository userAccountRepository,
            OrderRepository orderRepository
    ){
        this.userAccountRepository = userAccountRepository;
        this.orderRepository = orderRepository;
    }



    @Override
    public List<CustomerResponse> getAllCustomers() {

        List<UserAccount> customers =
                userAccountRepository.findByRole(Role.CUSTOMER);


        List<CustomerResponse> responses =
                new ArrayList<>();


        for(UserAccount customer : customers){


            List<Order> orders =
                    orderRepository.findByUserOrderByOrderedAtDesc(customer);


            BigDecimal totalSpent = BigDecimal.ZERO;


            for(Order order : orders){

                totalSpent =
                        totalSpent.add(order.getTotalAmount());

            }


            responses.add(
                    new CustomerResponse(

                            customer.getId(),

                            customer.getFullName(),

                            customer.getEmail(),

                            customer.getPhone(),

                            (long) orders.size(),

                            totalSpent,

                            customer.getCreatedAt()

                    )
            );
        }


        return responses;
    }
}