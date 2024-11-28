package com.uber.uber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uber.uber.entities.UserEntity;
import com.uber.uber.repositories.UserRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    UserRepo userRepo;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntity user = userRepo.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username: " + username)
        );
        UserDetails userDetails = User.builder()
                                      .username(user.getUsername())
                                      .password(user.getPassword())
                                      .roles(user.getRole())
                                      .build();
        return userDetails;
    }
}

