package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.RegisterRequest;

public interface UserAccountService {
    String register(RegisterRequest request);
}
