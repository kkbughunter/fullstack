package com.application.backend.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.application.backend.projection.UserBasicView;
import com.application.backend.projection.UserProfileView;
import com.application.backend.projection.UserReportView;
import com.application.backend.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // --- 1. Get basic user info ---
    public List<UserBasicView> getAllUsersBasic() {
        return userRepo.findAllProjectedBy();
    }

    // --- 2. Get user + profile details ---
    public List<UserProfileView> getAllUserProfiles() {
        return userRepo.getUserProfileDetails();
    }

    // --- 3. Get user report with functions ---
    public List<UserReportView> getUserReport() {
        return userRepo.getUserReport();
    }
}
