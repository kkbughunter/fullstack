package com.application.backend.constants;

public class RoleConstants {
    public static final String ADMIN = "ADMIN";
    public static final String SUPER_ADMIN = "SUPER_ADMIN";
    public static final String USER = "USER";
    public static final String GUEST = "GUEST";
    
    // Role groups for permissions
    public static final String ADMIN_ROLES = "hasRole('" + ADMIN + "') or hasRole('" + SUPER_ADMIN + "')";
    public static final String USER_ROLES = "hasRole('" + USER + "') or hasRole('" + GUEST + "')";
}