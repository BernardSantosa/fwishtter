package com.fwishtter.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequestDto {

    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "handle")
    private String handle;

    @JsonProperty(value = "bio")
    private String bio;

    @JsonProperty(value = "address")
    private String address;
}
