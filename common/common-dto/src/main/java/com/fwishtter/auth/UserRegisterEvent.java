package com.fwishtter.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterEvent {

    private String userId;
    private String username;
    private String email;

}
