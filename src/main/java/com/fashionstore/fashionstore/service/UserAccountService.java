package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.*;

public interface UserAccountService {
    String register(RegisterRequest request);
    String login(LoginRequest request);
    UserProfileResponse getProfile();
    String updateProfile(UpdateProfileRequest request);
    String changePassword(ChangePasswordRequest request);
}
