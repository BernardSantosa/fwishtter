package com.fwishtter.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TweetMediaListDto {

    @JsonProperty(value = "media_url")
    private String mediaUrl;

}
