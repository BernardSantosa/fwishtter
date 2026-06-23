package com.fwishtter.entity.fweesht;

import com.fwishtter.entity.common.UserDateAudit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Tweet extends UserDateAudit {

    @Column(name = "author_id", nullable = false)
    private UUID author_id;

    @Column(name = "parent_id", nullable = false)
    private UUID parent_id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TweetMedia> mediaList;
}
