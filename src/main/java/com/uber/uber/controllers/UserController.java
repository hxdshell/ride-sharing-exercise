package com.uber.uber.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uber.uber.entities.DriverEntity;
import com.uber.uber.entities.PassengerEntity;
import com.uber.uber.entities.UserEntity;
import com.uber.uber.records.UserGeneral;
import com.uber.uber.records.UserLogin;
import com.uber.uber.repositories.UserRepo;
import com.uber.uber.services.JwtService;
import com.uber.uber.services.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public UserController(UserRepo userRepo, UserService userService,AuthenticationManager authenticationManager,JwtService jwtService){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLogin credentials){
        try{
            Authentication authRequest = UsernamePasswordAuthenticationToken.unauthenticated(credentials.username(),credentials.password());
            Authentication auth = this.authenticationManager.authenticate(authRequest);
            
            System.out.println(auth); //----

            String token = jwtService.generateToken(credentials.username());
            return ResponseEntity.ok().body(token);
        }
        catch(Exception ex){
            return ResponseEntity.status(401).body("Failed");
        }
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
