package com.uber.uber.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uber.uber.Entities.UserEntity;

public interface UserRepo extends CrudRepository<UserEntity, Integer>{
}
