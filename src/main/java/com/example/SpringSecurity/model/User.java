package com.example.SpringSecurity.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email_id;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "user_type")
    private String user_type;

    @Column(name = "login_count")
    private int loginCount;

//    @Column(name = "created")
//    private LocalDateTime created;
//
//    @Column(name = "updated")
//    private LocalDateTime updated;
}
