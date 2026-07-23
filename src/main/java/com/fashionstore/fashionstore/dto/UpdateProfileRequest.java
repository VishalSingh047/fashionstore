package com.fashionstore.fashionstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequest {

    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Phone Number is required")
    private String phone;
}