package org.education.beerlovers.service;

import org.education.beerlovers.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
  List<UserEntity> getAllUsers();
  UserEntity findById(Long id);
  UserEntity addNewBeer(UserEntity userEntity);
  UserEntity updateBeer(UserEntity userEntity);
}
