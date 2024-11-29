package com.uber.uber.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rides")
public class RideEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "passenger")
    private UUID passengerId;

    @Column(name = "driver")
    private UUID driverId;

    @Column(name = "ride_type")
    private String rideType;

    @Column(name = "pickup_location", columnDefinition = "geometry(Point,4326)")
    private Point<G2D> pickupLocation;

    @Column(name = "destination_location", columnDefinition = "geometry(Point,4326)")
    private Point<G2D> destinationLocation;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "arrived_at")
    private LocalDateTime arrivedAt;

    @Column(name = "ride_status")
    private String rideStatus;

    public Long getId(){ return id; }
    public UUID getPassengerId(){ return passengerId; }
    public UUID getDriverId(){ return driverId; }
    public String getRideType() { return rideType; }
    public Point<G2D> getPickupLocation() { return pickupLocation; }
    public Point<G2D> getDestinationLocation() { return destinationLocation; }
    public LocalDateTime getPickupTime() { return pickupTime; }
    public LocalDateTime getArrivalTime() { return arrivedAt; }
    public String getRideStatus() { return rideStatus; }

    public void setPassengerId(UUID passengerId){ this.passengerId = passengerId; }
    public void setDriverId(UUID driverId){ this.driverId = driverId; }
    public void setRideType(String rideType) { this.rideType = rideType; }
    public void setPickupLocation(Point<G2D> pickupLocation) { this.pickupLocation = pickupLocation; }
    public void setDestinationLocation(Point<G2D> destinationLocation) { this.destinationLocation = destinationLocation; }
    public void setPickupTime(LocalDateTime pickupTime) { this.pickupTime = pickupTime; }
    public void setArrivalTime(LocalDateTime arrivedAt ) { this.arrivedAt = arrivedAt; }
    public void setRideStatus(String rideStatus) { this.rideStatus = rideStatus; }

}
