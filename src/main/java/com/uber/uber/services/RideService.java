package com.uber.uber.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uber.uber.entities.RideEntity;
import com.uber.uber.records.RideAccept;
import com.uber.uber.records.RideReqest;
import com.uber.uber.records.RideSearch;
import com.uber.uber.records.RideSearchDriverDTO;
import com.uber.uber.repositories.RideRepo;

import jakarta.transaction.Transactional;

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
    public List<RideSearchDriverDTO> proximitySearch(RideSearch params){
        
        double lon = params.longitude();
        double lat = params.latitude();
        double dist = params.distance();
        
        List<Object[]> rows = rideRepo.findByProximity(lon,lat,dist);

        Long id;
        Integer userId;
        String username;
        double[] pickLocation = new double[2];
        LocalDateTime pickupTime;
        String rideType;

        List<RideSearchDriverDTO> rides = new ArrayList<>();

        for (Object[] row : rows) {
            id = (Long) row[0];
            userId = (Integer) row[1];
            username = (String) row[2];
            ((Point<?>)row[3]).getPosition().toArray(pickLocation);
            pickupTime = ((Timestamp) row[4]).toLocalDateTime();
            rideType = (String) row[5];

            rides.add(new RideSearchDriverDTO(id, userId, username, pickLocation, pickupTime, rideType));
        }
        
        return rides;
    }

    @Transactional    
    public int acceptRide(RideAccept details){
        UUID driver = UUID.fromString(details.driverId());
        Long rideId = details.rideId();
        
        int rows = rideRepo.setRideDriver(driver, rideId);
        return rows;
    }
}
