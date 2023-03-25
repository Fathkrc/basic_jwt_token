package com.spring_jwt.service;

import com.spring_jwt.dto.RegisterRequest;
import com.spring_jwt.model.Role;
import com.spring_jwt.model.User;
import com.spring_jwt.model.UserRoles;
import com.spring_jwt.repository.RoleRepo;
import com.spring_jwt.repository.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserServiceTest {
    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    public void build(){
        userRepo= mock(UserRepo.class);
        roleRepo=mock(RoleRepo.class);
        passwordEncoder=mock(PasswordEncoder.class);
        userService=new UserService(userRepo,roleRepo,passwordEncoder);
    }
//
//    @Test
//    public void isUserCanRegisterWithAnyValidRegisterRequest(){
//        Set<Role> roles= new HashSet<>();
//        roles.add(new Role(2,UserRoles.ROLE_USER));
//        RegisterRequest request=
//                new RegisterRequest("firstname",
//                        "lastname",
//                        "username",
//                        "password",
//                        roles);
//
//        User user=new User(20l,"firstname",
//                "lastname",
//                "username","password" , roles );
//        Mockito.when(passwordEncoder.encode("password")).thenReturn("password");
//        Mockito.verify(passwordEncoder).encode("password");
//        assertDoesNotThrow((Executable) userRepo.save(user));
//
//    }

}