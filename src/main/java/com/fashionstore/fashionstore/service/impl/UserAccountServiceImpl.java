package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.*;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.enums.Role;
import com.fashionstore.fashionstore.repository.UserAccountRepository;
import com.fashionstore.fashionstore.security.JwtService;
import com.fashionstore.fashionstore.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {
        if (userAccountRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        UserAccount user = new UserAccount();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(Role.CUSTOMER);

        userAccountRepository.save(user);

        return "Registered Successfully";
    }

    @Override
    public String login(LoginRequest request){
        UserAccount user = userAccountRepository.findByEmail(request.getEmail()).orElse(null);

        if(user == null){
            return "Invalid email";
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return "Invalid password";
        }

        return jwtService.generateToken(user.getEmail());
    }

    @Override
    public UserProfileResponse getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        UserAccount user = userAccountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found"));

        return new UserProfileResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );

    }

    @Override
    public String updateProfile(UpdateProfileRequest request) {

        // Get Logged-in User
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        // Fetch User
        UserAccount user = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // Update Details
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());

        // Save Updated User
        userAccountRepository.save(user);

        return "Profile updated successfully";
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        UserAccount user = userAccountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return "Current password is incorrect";
        }

        // Prevent same password
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            return "New password cannot be the same as current password";
        }

        // Save encrypted password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userAccountRepository.save(user);

        return "Password changed successfully";
    }
}