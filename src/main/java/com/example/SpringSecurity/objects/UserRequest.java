package com.example.SpringSecurity.objects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String userName;
    private String password;
    private String email_id;
    private boolean isActive;
    private String user_type;

}
