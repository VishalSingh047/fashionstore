package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.dto.LoginRequest;
import com.fashionstore.fashionstore.dto.RegisterRequest;
import com.fashionstore.fashionstore.dto.UpdateProfileRequest;
import com.fashionstore.fashionstore.dto.UserProfileResponse;
import com.fashionstore.fashionstore.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.fashionstore.fashionstore.dto.ChangePasswordRequest;

@RestController
@RequestMapping("/api/users")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request){
        return userAccountService.register(request);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request){
        return userAccountService.login(request);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfileResponse getProfile(){
        return userAccountService.getProfile();
    }

    @PutMapping("/update-profile")
    @PreAuthorize("isAuthenticated()")
    public String updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        return userAccountService.updateProfile(request);
    }

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public String changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        return userAccountService.changePassword(request);
    }
}
