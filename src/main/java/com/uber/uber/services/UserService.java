package com.uber.uber.services;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    UserRepo userRepo;
    DriverRepo driverRepo;
    PassengerRepo passengerRepo;

    @Autowired
    public UserService(UserRepo userRepo,DriverRepo driverRepo, PassengerRepo passengerRepo){
        this.userRepo = userRepo;
        this.driverRepo = driverRepo;
        this.passengerRepo = passengerRepo;
    }

    @Transactional
    public void registerUser(UserEntity user, DriverEntity driver){
        user = userRepo.save(user);
        System.out.println(user);
        driver.setUserId(user.getId());
        driverRepo.save(driver);
    }

    @Transactional
    public void registerUser(UserEntity user, PassengerEntity passenger){
        user = userRepo.save(user);
        passenger.setUserId(user.getId());
        passengerRepo.save(passenger);
    }
}
