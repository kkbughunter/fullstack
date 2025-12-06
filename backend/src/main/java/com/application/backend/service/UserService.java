package com.application.backend.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.application.backend.dto.UserInputDto;
import com.application.backend.model.Profile;
import com.application.backend.model.User;
import com.application.backend.projection.UserBasicView;
import com.application.backend.projection.UserProfileView;
import com.application.backend.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // CREATE
    public UserBasicView createUser(UserInputDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());

        Profile profile = new Profile();
        profile.setFirstName(input.getFirstName());
        profile.setLastName(input.getLastName());
        profile.setAge(input.getAge());

        profile.setUser(user);
        user.setProfile(profile);

        User saved = userRepo.save(user);
        return userRepo.findProjectedById(saved.getId());
    }

    // UPDATE
    public UserBasicView updateUser(Long id, UserInputDto input) {
        User user = userRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
            user.setProfile(profile);
        }
        profile.setFirstName(input.getFirstName());
        profile.setLastName(input.getLastName());
        profile.setAge(input.getAge());

        userRepo.save(user);
        return userRepo.findProjectedById(id);
    }

    // DELETE
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    // READ (Projections)
    public List<UserBasicView> getAllUsersBasic() {
        return userRepo.findAllProjectedBy();
    }

    public List<UserProfileView> getAllUserProfiles() {
        return userRepo.getUserProfileDetails();
    }
}