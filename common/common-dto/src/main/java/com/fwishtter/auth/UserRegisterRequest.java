package com.fwishtter.userservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotNull(message = "Please fill out the username section")
    @Size(min = 1, max = 255)
    @JsonProperty(value = "display_name")
    private String displayName;

    @NotNull(message = "Please fill out the email section")
    @Size(min = 1, max = 255)
    @JsonProperty(value = "email")
    private String email;

    @NotNull(message = "Please fill out the password section")
    @Size(min = 1, max = 255)
    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "re_type_password")
    @NotNull(message = "Please confirm the password")
    private String reTypePassword;

    @JsonProperty(value = "handle")
    private String handle;

    @JsonProperty(value = "phone_number")
    @NotNull(message = "Please input phone number")
    private String phoneNumber;

    @Nullable
    private String bio;
}
