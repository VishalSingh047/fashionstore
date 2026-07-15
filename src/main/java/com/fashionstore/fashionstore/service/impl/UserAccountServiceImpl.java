package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.RegisterRequest;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.enums.Role;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterRequest request) {
        if (userAccountRepo.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        UserAccount user = new UserAccount();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(Role.CUSTOMER);

        userAccountRepo.save(user);

        return "Registered Successfully";
    }

}