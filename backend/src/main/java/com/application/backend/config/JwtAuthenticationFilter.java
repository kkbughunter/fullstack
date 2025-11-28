package com.application.backend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null && validateToken(token)) {
            try {
                byte[] keyBytes = jwtSecret.getBytes();
                if (keyBytes.length < 32) {
                    byte[] paddedKey = new byte[32];
                    System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
                    keyBytes = paddedKey;
                }

                Claims claims = Jwts.parser().verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes)).build().parseSignedClaims(token).getPayload();
                String userId = claims.getSubject();
                String roleCode = claims.get("roleCode", String.class);

                // System.out.println("JWT Filter - UserId: " + userId + ", RoleCode: " + roleCode);

                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleCode));
                // System.out.println("JWT Filter - Authorities: " + authorities);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // System.out.println("JWT Filter - Authentication set successfully");
            } catch (Exception e) {
                // System.out.println("JWT Filter - Error processing token: " + e.getMessage());
            }
        } else {
            // System.out.println("JWT Filter - No valid token found");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            byte[] keyBytes = jwtSecret.getBytes();
            if (keyBytes.length < 32) {
                byte[] paddedKey = new byte[32];
                System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.length);
                keyBytes = paddedKey;
            }
            Jwts.parser().verifyWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes)).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // System.out.println("Token validation failed: " + e.getMessage());
            return false;
        }
    }
}