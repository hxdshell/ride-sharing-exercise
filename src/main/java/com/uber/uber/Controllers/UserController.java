package com.uber.uber.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uber.uber.entities.DriverEntity;
import com.uber.uber.entities.PassengerEntity;
import com.uber.uber.entities.UserEntity;
import com.uber.uber.records.UserGeneral;
import com.uber.uber.repositories.UserRepo;
import com.uber.uber.services.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private UserRepo userRepo;
    private UserService userService;

    @Autowired
    public UserController(UserRepo userRepo, UserService userService){
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @GetMapping("/find")
    public String find(){
        UserEntity user = userRepo.findById(2).get();
        return user.getId() + " " + user.getUsername() + " " + user.getPassword();
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserGeneral user){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.username());
        userEntity.setName(user.name());
        userEntity.setPhone(user.phone());
        userEntity.setRole(user.role());
        userEntity.setPassword(user.password());

        try{
            if (user.role().equals("DRIVER")){
                DriverEntity driverEntity = new DriverEntity();
                driverEntity.setLicensePlate(user.licencePlate().get());
                driverEntity.setCurrentStatus("AVAILABLE");
                userService.registerUser(userEntity, driverEntity);
            }
            else if(user.role().equals("PASSENGER")){
                PassengerEntity passengerEntity = new PassengerEntity();
                userService.registerUser(userEntity, passengerEntity);    
            }
        }
        catch(NoSuchElementException nseex){
            nseex.printStackTrace();
            return ResponseEntity.badRequest().body("You must enter licence plate");
        }
        catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to register the user check your input and try again");
        }
        return ResponseEntity.ok().body("Success");
    }
}
