package com.fwishtter.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTweetRequestDto {

    @Nullable
    @JsonProperty(value = "parent_id")
    private String parentId;

    @NotBlank(message = "content cannot be empty")
    @JsonProperty(value = "content")
    private String content;

    @NotBlank(message = "type cannot be empty")
    @JsonProperty(value = "type")
    private String type;

    @Nullable
    @JsonProperty(value = "media_list")
    private List<TweetMediaListDto> mediaList;

}
