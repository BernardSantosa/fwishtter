package fwishtter.com.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Column(unique = true)
    private String display_name;

    @Column(unique = true)
    private String userId;

    @Column(unique = true)
    private String email;
    private String password;

    private String handle;

    @Column(unique = true)
    private String phone_number;

    private String text;

    private String profile_picture;

    // for Jwt
    private String token;
    private Long expired_at;

//    @CreatedDate
//    @Column(updatable = false)
//    private Instant createdAt;
//
//    @LastModifiedDate
//    private Instant updatedAt;
}

