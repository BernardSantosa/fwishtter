package com.fwishtter.entity.fweesht;

import com.fwishtter.entity.common.IdDateAudit;
import com.fwishtter.entity.common.UserDateAudit;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tweets")
public class Tweet extends IdDateAudit {

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy ;

    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TweetMedia> mediaList;
}
