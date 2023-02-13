package com.spring_jwt.security.service;

import com.spring_jwt.exception.ResourceNotFoundException;
import com.spring_jwt.model.User;
import com.spring_jwt.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepo.findByUserName(username).orElseThrow(
                ()-> new ResourceNotFoundException("User not found")
        );
        return UserDetailsImpl.build(user);
    }
}
