package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.LoginRequest;
import com.fashionstore.fashionstore.dto.RegisterRequest;
import com.fashionstore.fashionstore.dto.UserProfileResponse;

public interface UserAccountService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
    UserProfileResponse getProfile();
}
