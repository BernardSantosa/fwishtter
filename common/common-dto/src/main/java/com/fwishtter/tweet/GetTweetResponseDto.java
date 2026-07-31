package com.fwishtter.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTweetResponseDto {

    @JsonProperty(value = "display_name")
    private String displayName;

    @JsonProperty(value = "handle")
    private String handle;

    @JsonProperty(value = "content")
    private String content;

    @JsonProperty(value = "created_time")
    private String createdTime;

    @JsonProperty(value = "updated_time")
    private String updatedTime;

    @JsonProperty(value = "created_by")
    private String createdBy;

    @JsonProperty(value = "updated_by")
    private String updatedBy;

    @JsonProperty(value = "media_list")
    private List<TweetMediaListDto> mediaList;
}
