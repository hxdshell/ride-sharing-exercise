package com.uber.uber.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.uber.uber.entities.PassengerEntity;

public interface PassengerRepo extends CrudRepository<PassengerEntity, UUID>{
}
