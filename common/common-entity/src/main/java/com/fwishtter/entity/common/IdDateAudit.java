package fwishtter.com.entity.common;

import fwishtter.com.converter.LocalDateTimeType;
import fwishtter.com.converter.VarcharUuidType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.java.DataHelper;
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
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Type(VarcharUuidType.class)
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
            this.setCreatedTime(DataHelper.);
        }
    }
}
