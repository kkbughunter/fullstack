package com.application.backend.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private String refreshToken;
    private String userId;
    private String roleCode;
    private String landingUrl;
    
    public LoginResponseDto(String token, String refreshToken, String userId, String roleCode, String landingUrl) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.roleCode = roleCode;
        this.landingUrl = landingUrl;
    }
}