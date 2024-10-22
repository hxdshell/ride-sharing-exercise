package com.uber.uber.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.uber.uber.Entities.DriverEntity;


public interface DriverRepo extends CrudRepository<DriverEntity, UUID>{
    List<DriverEntity> findByCurrentStatus(String currentStatus);
}
