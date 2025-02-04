package org.example.blogagain.payload;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}

