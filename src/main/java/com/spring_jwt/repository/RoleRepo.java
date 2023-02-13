package com.spring_jwt.repository;

import com.spring_jwt.model.Role;
import com.spring_jwt.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(UserRoles role);
}
