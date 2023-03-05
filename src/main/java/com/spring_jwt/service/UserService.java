package com.spring_jwt.service;


import com.spring_jwt.dto.RegisterRequest;
import com.spring_jwt.exception.ConflictException;
import com.spring_jwt.exception.ResourceNotFoundException;
import com.spring_jwt.model.Role;
import com.spring_jwt.model.User;
import com.spring_jwt.model.UserRoles;
import com.spring_jwt.repository.RoleRepo;
import com.spring_jwt.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerRequest(RegisterRequest registerRequest) {
        if(userRepo.existsByUserName(registerRequest.getUserName())){
            throw new ConflictException("This username is  already in use");
        }
        Role role=roleRepo.findByName(UserRoles.ROLE_ADMIN).orElseThrow(
                ()-> new ResourceNotFoundException("Role not Found")
        );
        Set<Role> roleSet=new HashSet<>();
        roleSet.add(role);

        User user=new User();
        user.setFirstName(registerRequest.getName());
        user.setUserName(registerRequest.getUserName());
        user.setLastName(registerRequest.getLastName());
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepo.save(user);

    }
}
