package com.uber.uber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uber.uber.entities.DriverEntity;
import com.uber.uber.entities.PassengerEntity;
import com.uber.uber.entities.UserEntity;
import com.uber.uber.repositories.DriverRepo;
import com.uber.uber.repositories.PassengerRepo;
import com.uber.uber.repositories.UserRepo;

@Service
public class UserService {
    
    private UserRepo userRepo;
    private DriverRepo driverRepo;
    private PassengerRepo passengerRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo,DriverRepo driverRepo, PassengerRepo passengerRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.driverRepo = driverRepo;
        this.passengerRepo = passengerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerUser(UserEntity user, DriverEntity driver){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        driver.setUserId(user.getId());
        driverRepo.save(driver);
    }

    @Transactional
    public void registerUser(UserEntity user, PassengerEntity passenger){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        passenger.setUserId(user.getId());
        passengerRepo.save(passenger);
    }
}
