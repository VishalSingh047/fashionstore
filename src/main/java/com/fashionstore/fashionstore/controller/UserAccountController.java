package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.dto.RegisterRequest;
import com.fashionstore.fashionstore.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){
        return userAccountService.register(request);
    }
}
