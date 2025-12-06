package com.application.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.application.backend.model.User;
import com.application.backend.projection.UserBasicView;
import com.application.backend.projection.UserProfileView;

public interface UserRepo extends JpaRepository<User, Long> {

    // Simple projection
    List<UserBasicView> findAllProjectedBy();

    UserBasicView findProjectedById(Long id);

    // Join projection
    @Query("""
        SELECT 
            u.username AS username,
            p.firstName AS firstName,
            p.lastName AS lastName,
            p.age AS age
        FROM User u
        JOIN u.profile p
    """)
    List<UserProfileView> getUserProfileDetails();
}
