package com.uber.uber.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uber.uber.entities.RideEntity;

public interface RideRepo extends CrudRepository<RideEntity,Long>{
    
}
