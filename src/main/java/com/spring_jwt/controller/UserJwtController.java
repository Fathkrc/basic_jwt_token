package com.spring_jwt.controller;

import com.spring_jwt.dto.LoginRequest;
import com.spring_jwt.dto.RegisterRequest;
import com.spring_jwt.security.JwtUtils;
import com.spring_jwt.security.service.UserDetailsImpl;
import com.spring_jwt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJwtController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    //---------------REGİSTER---------------

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest){

        userService.registerRequest(registerRequest);
        return ResponseEntity.ok("User has been saved ");
        //or return new  ResponseEntity<>("User has been saved ",HttpStatus.OK)

    }
    //------------------------LOGIN-----------------------------

    @PostMapping("/login")
    public ResponseEntity<String> loginRequest(@Valid @RequestBody LoginRequest loginRequest){
      Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                ));
       String token= jwtUtils.generateToken(authentication);
        return new ResponseEntity<>( token, HttpStatus.CREATED);
    }

}
