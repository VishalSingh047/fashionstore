package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.dto.CustomerResponse;
import com.fashionstore.fashionstore.service.AdminCustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/customers")
public class AdminCustomerController {


    private final AdminCustomerService customerService;


    public AdminCustomerController(
            AdminCustomerService customerService
    ){
        this.customerService = customerService;
    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getCustomers(){


        return ResponseEntity.ok(
                ApiResponse.success(
                        "Customers fetched successfully",
                        customerService.getAllCustomers()
                )
        );
    }

}