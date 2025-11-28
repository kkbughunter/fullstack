package com.application.backend.service;

import com.application.backend.dto.LoginRequestDto;
import com.application.backend.dto.LoginResponseDto;
import com.application.backend.mapper.LoginMapper;
import com.application.backend.model.Role;
import com.application.backend.model.User;
import com.application.backend.model.UserRoleMap;
import com.application.backend.repo.RoleRepository;
import com.application.backend.repo.UserRepository;
import com.application.backend.repo.UserRoleMapRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserRoleMapRepository userRoleMapRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginMapper loginMapper;


    @Value("${jwt.secret}")
    private String jwtSecret;
    
    public LoginResponseDto login(LoginRequestDto request) {
        System.out.println("Login attempt for email: " + request.getEmail());
        Optional<User> userOpt = userRepository.findByEmailAndIsActiveTrue(request.getEmail());
        
        if (userOpt.isEmpty()) {
            System.out.println("User not found for email: " + request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
        
        User user = userOpt.get();
        System.out.println("User found: " + user.getEmail() + ", stored password: " + user.getPassword());
        
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        System.out.println("Password matches: " + passwordMatches);
        
        if (!passwordMatches) {
            System.out.println("Password mismatch for user: " + request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
        
        System.out.println("Password validated, looking for role mapping for userId: " + user.getUserId());
        Optional<UserRoleMap> roleMapOpt = userRoleMapRepository.findByUserIdAndIsActiveTrue(user.getUserId());
        
        if (roleMapOpt.isEmpty()) {
            System.out.println("No role mapping found for userId: " + user.getUserId());
            throw new RuntimeException("No role assigned");
        }
        
        System.out.println("Role mapping found, roleId: " + roleMapOpt.get().getRoleId());
        Role role = roleRepository.findById(roleMapOpt.get().getRoleId()).orElseThrow();
        System.out.println("Role found: " + role.getRoleCode());
        System.out.println("Role found: " + role.getLandingUrl());
        
        System.out.println("Creating JWT token...");
        try {
            // Ensure JWT secret is at least 32 bytes for HS256
            byte[] keyBytes = jwtSecret.getBytes();
            if (keyBytes.length < 32) {
                // Pad the key if it's too short
                byte[] paddedKey = new byte[32];
                System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
                keyBytes = paddedKey;
            }
            
            // Access token (1 hour)
            String token = Jwts.builder()
                    .setSubject(user.getUserId())
                    .claim("roleCode", role.getRoleCode())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes))
                    .compact();
            
            // Refresh token (7 days)
            String refreshToken = Jwts.builder()
                    .setSubject(user.getUserId())
                    .claim("type", "refresh")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                    .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes))
                    .compact();
            
            System.out.println("JWT tokens created successfully");

            // return new LoginResponseDto(token, refreshToken, user.getUserId(), role.getRoleCode(), role.getLandingUrl());
            return loginMapper.toLoginResponseDto(
                    token,
                    refreshToken,
                    user.getUserId(),
                    role.getRoleCode(),
                    role.getLandingUrl()
            );

        } catch (Exception e) {
            System.out.println("JWT creation failed: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Token generation failed");
        }
    }
    
    public LoginResponseDto refreshToken(String refreshToken) {
        try {
            byte[] keyBytes = jwtSecret.getBytes();
            if (keyBytes.length < 32) {
                byte[] paddedKey = new byte[32];
                System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
                keyBytes = paddedKey;
            }
            
            Claims claims = Jwts.parser()
                .verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload();

            String userId = claims.getSubject();
            String tokenType = claims.get("type", String.class);
            
            if (!"refresh".equals(tokenType)) {
                throw new RuntimeException("Invalid token type");
            }
            
            User user = userRepository.findById(userId).orElseThrow();
            UserRoleMap roleMap = userRoleMapRepository.findByUserIdAndIsActiveTrue(userId).orElseThrow();
            Role role = roleRepository.findById(roleMap.getRoleId()).orElseThrow();
            
            // Generate new access token
            String newToken = Jwts.builder()
                    .setSubject(userId)
                    .claim("roleCode", role.getRoleCode())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes))
                    .compact();
            
            // return new LoginResponseDto(newToken, refreshToken, userId, role.getRoleCode(), role.getLandingUrl());
            return loginMapper.toLoginResponseDto(
                    newToken,
                    refreshToken,
                    userId,
                    role.getRoleCode(),
                    role.getLandingUrl()
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}