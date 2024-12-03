package com.uber.uber.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.uber.uber.entities.RideEntity;

public interface RideRepo extends CrudRepository<RideEntity,Long>{
    
    @Query(value = "SELECT r.id, u.id AS userId, u.username, r.pickup_location AS pickupLocation, r.pickup_time AS pickupTime, r.ride_type AS rideType FROM rides AS r "+
                        "JOIN passengers ON r.passenger = passengers.id "+
                        "JOIN users AS u ON passengers.user_id = u.id "+
                        "WHERE ST_DWithin( "+
                        "pickup_location::geography,"+
                        "ST_GeographyFromText('POINT(' || :lon || ' ' || :lat || ')'),:dist) "+"AND r.ride_status = 'PENDING' "+
                        "AND r.pickup_time::date = CURRENT_DATE;", nativeQuery = true)
    List<Object[]> findByProximity(
        @Param("lon") double lon, 
        @Param("lat") double lat, 
        @Param("dist") double distance);

    @Modifying
    @Query(value = "UPDATE rides SET driver = :driver, ride_status = 'ACCEPTED' WHERE id = :rideId AND ride_status = 'PENDING'; ", nativeQuery = true)
    int setRideDriver(@Param("driver") UUID driver, @Param("rideId") Long rideId);
}
