package com.uber.uber.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "drivers")
public class DriverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "license_plate")
    private String licencsePlate;

    @Column(name = "current_status")
    private String currentStatus;

    public UUID getId() { return this.id; }
    public Integer getUserId() { return this.userId; }
    public String getLicensePlate() { return this.licencsePlate; }
    public String getCurrentStatus() { return this.currentStatus; }


    public void setUserId(Integer userId){ this.userId = userId; }
    public void setLicensePlate(String licensePlate){ this.licencsePlate = licensePlate; }
    public void setCurrentStatus(String currentStatus){ this.currentStatus = currentStatus; }

}
