package com.yuldashov.weathermvc.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "expiresat")
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public Session() {
    }
}
