package com.application.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "profiles")
public class Profile {

    @Id
    private Long id;

    private String firstName;
    private String lastName;
    private Integer age;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
