package com.spring_jwt.repository;

import com.spring_jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);

}
