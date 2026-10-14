package com.yuldashov.weathermvc.repository;

import com.yuldashov.weathermvc.model.Location;
import com.yuldashov.weathermvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    List<Location> findByUser(User user);
}
