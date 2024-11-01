package com.uber.uber.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uber.uber.entities.UserEntity;

import java.util.Optional;

public interface UserRepo extends CrudRepository<UserEntity, Integer>{
    Optional<UserEntity> findByUsername(String username); 
}
