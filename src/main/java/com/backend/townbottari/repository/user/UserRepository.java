package com.backend.townbottari.repository.user;


import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRoleAndSocialId(Role role, String socialId);

    @Query(value = "SELECT u.refresh_token FROM Users u WHERE u.id = :id", nativeQuery = true)
    String findRefreshTokenById(@Param("id") Long userId);
}
