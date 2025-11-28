package com.application.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        // Creates CORS policy
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");  	// Allows requests from ANY domain for mobile app request also.
        configuration.addAllowedMethod("*");  // Permits ALL HTTP methods
        configuration.addAllowedHeader("*");  // Allows ALL request headers
        configuration.setAllowCredentials(true); // Enables cookies, Authorization headers, session data - sends Access-Control-Allow-Credentials

        // Creates URL matcher - maps CORS rules to endpoint patterns
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applies rules to ALL endpoints (/** = every path) - preflight OPTIONS handled automatically
        return source;
    }
}