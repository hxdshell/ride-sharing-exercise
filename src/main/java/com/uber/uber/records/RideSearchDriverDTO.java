package com.uber.uber.records;

import java.time.LocalDateTime;


public record RideSearchDriverDTO(
    Long id,
    Integer userId,
    String username,
    double[] pickupLocation,
    LocalDateTime pickupTime,
    String rideType 
) {
}
