package com.uber.uber.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uber.uber.entities.RideEntity;
import com.uber.uber.records.RideReqest;
import com.uber.uber.repositories.RideRepo;

@Service
public class RideService {
    private RideRepo rideRepo;

    @Autowired
    public RideService(RideRepo rideRepo){
        this.rideRepo = rideRepo;
    }

    public Long place(RideReqest rideReqest){

        RideEntity rideEntity = new RideEntity();

        rideEntity.setRideType(rideReqest.rideType());
        rideEntity.setPassengerId(UUID.fromString(rideReqest.passengerId()));

        Point<G2D> pickLocation = Geometries.mkPoint(new G2D(rideReqest.pickupLong(), rideReqest.pickupLat()), CoordinateReferenceSystems.WGS84);
        Point<G2D> destinationLocation = Geometries.mkPoint(new G2D(rideReqest.destinationLong(), rideReqest.destinationLat()), CoordinateReferenceSystems.WGS84);

        rideEntity.setPickupLocation(pickLocation);
        rideEntity.setDestinationLocation(destinationLocation);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(rideReqest.pickupTime(),format);

        rideEntity.setPickupTime(dateTime);
        rideEntity.setRideStatus("PENDING");

        RideEntity saved = rideRepo.save(rideEntity);

        return saved.getId();
    }
}
