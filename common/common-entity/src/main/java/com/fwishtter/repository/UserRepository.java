package com.fwishtter.userservice.repository;

import com.fwishtter.entity.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        SELECT * FROM users
        WHERE display_name = :username
    """, nativeQuery = true)
    Optional<User> findUserByDisplayName(@Param("username") String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByToken(String token);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByHandle(String handle);
}
