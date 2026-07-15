package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.RegisterRequest;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository repository;

    @Override
    public String register(RegisterRequest request){
        return "Registration API under development";
    }

}