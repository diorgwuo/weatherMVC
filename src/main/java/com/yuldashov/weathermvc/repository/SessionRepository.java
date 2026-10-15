package com.yuldashov.weathermvc.repository;

import com.yuldashov.weathermvc.model.Session;
import com.yuldashov.weathermvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    boolean existsByUserAndExpiresAtAfter(User user, Instant now);

    @Query("SELECT s FROM Session s WHERE s.user = :user AND s.expiresAt > :now")
    Optional<Session> findSessionByUserAndExpiresAt(@Param("user") User user, @Param("now") Instant now);

    Optional<Session> findById(UUID id);

    @Query("SELECT s.user FROM Session s WHERE s.id = :id")
    Optional<User> findUserBySession(@Param("id") UUID id);
}
