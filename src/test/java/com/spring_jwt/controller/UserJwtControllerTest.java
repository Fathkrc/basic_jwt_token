package com.spring_jwt.controller;

import com.spring_jwt.dto.RegisterRequest;
import com.spring_jwt.model.Role;
import com.spring_jwt.model.User;
import com.spring_jwt.model.UserRoles;
import com.spring_jwt.security.JwtUtils;
import com.spring_jwt.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserJwtControllerTest {
    private UserJwtController controller;

    private  UserService userService;

    private  JwtUtils jwtUtils;

    private  AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
userService=Mockito.mock(UserService.class);
      jwtUtils=Mockito.mock(JwtUtils.class);
      authenticationManager=Mockito.mock(AuthenticationManager.class);
      controller=new UserJwtController(userService,jwtUtils,authenticationManager);


    }
    @Test
    void isValidUserRegisterRequestCanRegister(){
        Set<Role> set=new HashSet<>();

        set.add(new Role(1,UserRoles.ROLE_USER));

        RegisterRequest request=new RegisterRequest("fatih",
                "karaca",
                "username",
                "123456",set
        );
        Mockito.when(userService.registerRequest(request)).thenReturn(new User(1l,"fatih",
                "karaca",
                "username",
                "123456",set
                ));
        assertEquals(ResponseEntity.ok("User has been saved "),controller.registerUser(request));
        Mockito.verify(userService).registerRequest(request);

    }

    @Test
    void whenNotValidRequestTryToRegisterThrowException(){
        Set<Role> set=new HashSet<>();

        set.add(new Role(1,UserRoles.ROLE_USER));

        RegisterRequest request=new RegisterRequest("",
                "karaca",
                "username",
                "123456",set
        );
        Mockito.when(userService.registerRequest(request)).thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class,()->controller.registerUser(request));
        Mockito.verify(userService).registerRequest(request);
    }
}