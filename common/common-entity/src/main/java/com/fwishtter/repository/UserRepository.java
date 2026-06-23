package com.fwishtter.repository;

import com.fwishtter.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = """
        SELECT * FROM users
        WHERE display_name = :username
    """, nativeQuery = true)
    Optional<User> findUserByDisplayName(@Param("username") String username);

    Optional<User> findUserByEmail(String email);

//    Optional<User> findByToken(String token);

    Optional<User> findUserById(UUID id);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByHandle(String handle);

    Optional<User> findByDisplayNameAndEnabledIsTrue(String username);

    boolean existsByHandle(String candidate);
}
