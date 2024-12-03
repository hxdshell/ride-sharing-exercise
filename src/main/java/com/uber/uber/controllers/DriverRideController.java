package com.uber.uber.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uber.uber.records.RideAccept;
import com.uber.uber.records.RideSearch;
import com.uber.uber.records.RideSearchDriverDTO;
import com.uber.uber.services.RideService;

@RestController
@RequestMapping("/driver/ride")
public class DriverRideController {

    private RideService rideService;

    @Autowired
    public DriverRideController(RideService rideService){
        this.rideService = rideService;
    }
    
    @PostMapping("/search")
    public ResponseEntity<Object> search(@RequestBody RideSearch request){
        try {
            List<RideSearchDriverDTO> rides = rideService.proximitySearch(request);
            return ResponseEntity.ok().body(rides);
        } catch (Exception e) {
            HashMap<String,String> response = new HashMap<>();
            response.put("error", "Unable to fetch current rides.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<Object> accept(@RequestBody RideAccept request){
        
        HashMap<String,String> response = new HashMap<>();
        
        try {
            int rows = rideService.acceptRide(request);
            if (rows != 1){
                response.put("error", "Unable to find the ride");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            response.put("success", "Ride accepted succefully");
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            System.out.println(e);
            response.put("error", "Unable to accept the ride");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
