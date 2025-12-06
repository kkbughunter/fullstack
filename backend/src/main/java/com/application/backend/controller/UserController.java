package com.application.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.backend.dto.UserInputDto;
import com.application.backend.model.User;
import com.application.backend.projection.UserBasicView;
import com.application.backend.projection.UserProfileView;
import com.application.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE
    @PostMapping
    public UserBasicView createUser(@RequestBody UserInputDto input) {
        return userService.createUser(input);
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserBasicView updateUser(@PathVariable Long id, @RequestBody UserInputDto input) {
        return userService.updateUser(id, input);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }

    // GET: Basic info
    @GetMapping("/basic")
    public List<UserBasicView> getAllUsersBasic() {
        return userService.getAllUsersBasic();
    }

    // GET: Detailed info with Profile
    @GetMapping("/profiles")
    public List<UserProfileView> getAllUserProfiles() {
        return userService.getAllUserProfiles();
    }
}