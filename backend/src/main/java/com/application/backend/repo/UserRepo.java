package com.application.backend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.application.backend.model.User;
import com.application.backend.projection.UserBasicView;
import com.application.backend.projection.UserNativeView;
import com.application.backend.projection.UserProfileView;
import com.application.backend.projection.UserReportView;
import com.application.backend.projection.UserSummary;

public interface UserRepo extends JpaRepository<User, Long> {

    List<UserBasicView> findAllProjectedBy();

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

    @Query("""
        SELECT 
            CONCAT(p.firstName, ' ', p.lastName) AS fullName,
            UPPER(p.firstName) AS upperName,
            COUNT(u.id) AS userCount
        FROM User u
        JOIN u.profile p
        GROUP BY p.firstName, p.lastName
    """)
    List<UserReportView> getUserReport();

    @Query(value = """
        SELECT 
            u.username AS username,
            p.age AS age
        FROM users u
        JOIN profiles p ON u.id = p.user_id
        WHERE p.age > :age
    """, nativeQuery = true)
    List<UserNativeView> findUsersOlderThan(int age);

    @Query("""
        SELECT 
            u.username AS userName,
            u.email AS emailAddress
        FROM User u
    """)
    List<UserSummary> getUserSummary();

}
