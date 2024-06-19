package com.example.SpringSecurity.objects;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;
}
