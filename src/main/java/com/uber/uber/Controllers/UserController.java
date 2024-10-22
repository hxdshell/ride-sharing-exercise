package com.uber.uber.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uber.uber.Entities.UserEntity;
import com.uber.uber.repositories.UserRepo;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping("/find")
    public String findUser(){
        UserEntity user = userRepo.findById(2).get();
        return user.getId() + " " + user.getUsername() + " " + user.getPassword();
    }
}
