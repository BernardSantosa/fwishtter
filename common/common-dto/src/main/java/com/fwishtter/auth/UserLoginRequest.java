package com.fwishtter.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "Please fill out the email section")
    @Size(max = 100)
    private String email;

    @NotEmpty(message = "Please fill out the password section")
    @Size(min = 1, max = 100)
    private String password;

}
