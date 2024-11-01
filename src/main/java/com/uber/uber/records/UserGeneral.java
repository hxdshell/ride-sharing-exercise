package com.uber.uber.records;
import java.util.Optional;

public record UserGeneral(
    String username,
    String password,
    String name,
    String phone,
    String role,
    Optional<String> licencePlate,
    Optional<String> status
) {
    public UserGeneral(String username, String password, String name, String phone, String role){
        this(username,password,name,phone,role,Optional.empty(),Optional.empty());
    }
}
