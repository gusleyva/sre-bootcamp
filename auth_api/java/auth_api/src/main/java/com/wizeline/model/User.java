package com.wizeline.model;

import lombok.Data;

@Data
public class User {
    private String username;
    private String password;
    private String salt;
    private String role;
}