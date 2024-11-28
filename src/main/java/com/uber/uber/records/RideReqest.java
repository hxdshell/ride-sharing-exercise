package com.uber.uber.records;

public record RideReqest(
    String passengerId,
    String rideType,
    double pickupLat,
    double pickupLong,
    double destinationLat,
    double destinationLong,
    String pickupTime
) {
    
}
