package com.application.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "tblusers")
@Data
public class User {

    @Id
    @Column(length = 36)
    private String userId;

    @Column(name = "company_branch_id", length = 36)
    private String companyBranchId;

    @Column(name = "first_name", length = 200)
    private String firstName;

    @Column(name = "last_name", length = 200)
    private String lastName;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(name = "pwd", length = 300, nullable = false)
    private String password;

    @Column(name = "contact_no", length = 10, nullable = false)
    private String contactNo;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by", length = 100, nullable = false)
    private String createdBy;

    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "modified_by", length = 100)
    private String modifiedBy;

    @Column(name = "modified_dt")
    private LocalDateTime modifiedDt;

    // @OneToMany(mappedBy = "user")
    // private List<UserRoleMap> mappedRoles;
}
