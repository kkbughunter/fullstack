package com.application.backend.dto;

import lombok.Data;

@Data
public class UserInputDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;

}