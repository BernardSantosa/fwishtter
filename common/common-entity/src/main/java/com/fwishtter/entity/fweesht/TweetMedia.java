package com.fwishtter.entity.fweesht;

import com.fwishtter.entity.common.IdDateAudit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tweet_media")
public class TweetMedia extends IdDateAudit {

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweet tweet;

}
