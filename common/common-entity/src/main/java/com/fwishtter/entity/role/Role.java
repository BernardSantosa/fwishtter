package com.fwishtter.entity.role;

import com.fwishtter.entity.common.IdDateAudit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role extends IdDateAudit {

    @Column(unique = true, nullable = false, length = 20)
    private String name;

    private String description;
}
