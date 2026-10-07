package com.yuldashov.weathermvc.repository;

import com.yuldashov.weathermvc.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User save(User userToSave);

    User findByLogin(String login);


}
