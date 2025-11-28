package com.application.backend.controller;

import com.application.backend.constants.RoleConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @GetMapping("/dashboard")
    @PreAuthorize("@customSecurity.hasPermission('ADMIN_ACCESS')")
    public String adminDashboard() {
        return "Admin Dashboard - Users with ADMIN_ACCESS permission";
    }
    
    @GetMapping("/users")
    @PreAuthorize("@customSecurity.hasPermission('USER_MANAGEMENT')")
    public String manageUsers() {
        return "User Management - Users with USER_MANAGEMENT permission";
    }
}