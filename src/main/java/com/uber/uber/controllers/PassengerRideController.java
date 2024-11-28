package com.uber.uber.controllers;

import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uber.uber.records.RideReqest;
import com.uber.uber.services.RideService;

@RestController
@RequestMapping("/passenger/ride")
public class PassengerRideController {
    
    private RideService rideService;

    @Autowired
    public PassengerRideController(RideService rideService){
        this.rideService = rideService;
    }

    @PostMapping("/request")
    public ResponseEntity<Object> request(@RequestBody RideReqest rideReqest){

        Long id;
        try {
            id = rideService.place(rideReqest);
        } catch (DateTimeParseException dateEx) {
            System.out.println(dateEx.getMessage());
            return ResponseEntity.badRequest().body("Invalid Date. Check your date input");
        } catch(Exception e){
            System.out.println(e);
            return ResponseEntity.badRequest().body("Invalid request. Check your input");
        }

        return ResponseEntity.ok().body("ride request has been placed with id " + id);
    }
}
