package com.yuldashov.weathermvc.repository;

import com.yuldashov.weathermvc.models.Session;
import com.yuldashov.weathermvc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {

    @Query("SELECT COUNT(s)>0 FROM Session s WHERE s.user = :user AND s.expiresAt > :now")
    boolean existByUserAndExpiresAt(@Param("user") User user, @Param("now") LocalDateTime now);

    @Query("SELECT s FROM Session s WHERE s.user = :user AND s.expiresAt > :now")
    Optional<Session> findSessionByUser(@Param("user") User user, @Param("now") LocalDateTime now);

    @Query("SELECT COUNT(s)>0 FROM Session s WHERE s.id = :id AND s.expiresAt > :now")
    boolean checkSessionById(@Param("id") UUID id, @Param("now") LocalDateTime now);

    @Query("SELECT s.user FROM Session s WHERE s.id = :id")
    Optional<User> findUserBySession(@Param("id") UUID id);
}
