package com.yuldashov.weathermvc.repository;

import com.yuldashov.weathermvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User save(User userToSave);

    Optional<User> findByLogin(String login);
}
