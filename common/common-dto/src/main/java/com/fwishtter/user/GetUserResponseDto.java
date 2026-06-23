package com.fwishtter.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserResponseDto implements Serializable {

    @JsonProperty(value = "user_id")
    private UUID userId;

    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "handle")
    private String handle;

    @JsonProperty(value = "phone_number")
    private String phoneNumber;

    @JsonProperty(value = "bio")
    private String bio;

    @JsonProperty(value = "profile_picture_url")
    private String profilePicture;
}
