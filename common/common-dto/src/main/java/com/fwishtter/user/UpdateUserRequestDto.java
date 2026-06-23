package com.fwishtter.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequestDto {

    @NotNull(message = "Please fill out the username section")
    @Size(min = 1, max = 50)
    @JsonProperty("display_name")
    private String displayName;

    @NotNull(message = "Please fill out the handle (@name) section")
    @Size(min = 3, max = 15)
    @JsonProperty("handle")
    private String handle;

    @NotNull(message = "Please fill out the bio section")
    @JsonProperty(value = "bio")
    private String bio;

    @NotNull(message = "Please fill out the address section")
    @JsonProperty(value = "address")
    private String address;
}
