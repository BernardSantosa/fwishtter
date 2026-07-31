package com.fwishtter.entity.common;

import com.fwishtter.helper.DateHelper;
import com.fwishtter.converter.LocalDateTimeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class IdDateAudit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private UUID id;

    @Column(name = "created_time", nullable = false, updatable = false)
    @Type(LocalDateTimeType.class)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    @Type(LocalDateTimeType.class)
    private LocalDateTime updatedTime;

    @Transient
    private boolean dateTimeAsUTC = false;

    @PrePersist
    public void prePresist(){
        if(dateTimeAsUTC) {
            this.setCreatedTime(DateHelper.localDateTimeIso8601());
            this.setUpdatedTime(DateHelper.localDateTimeIso8601());
        } else {
            this.setCreatedTime(LocalDateTime.now());
            this.setUpdatedTime(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (dateTimeAsUTC) {
            this.setUpdatedTime(DateHelper.localDateTimeIso8601());
        } else {
            this.setUpdatedTime(LocalDateTime.now());
        }
    }
}
