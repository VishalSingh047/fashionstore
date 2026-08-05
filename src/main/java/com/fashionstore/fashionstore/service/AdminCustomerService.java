package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.CustomerResponse;

import java.util.List;

public interface AdminCustomerService {

    List<CustomerResponse> getAllCustomers();

}