package org.education.beerlovers.service.impl;

import org.education.beerlovers.entity.UserEntity;
import org.education.beerlovers.repository.UserRepository;
import org.education.beerlovers.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public UserEntity findById(Long id) {
    return userRepository.getReferenceById(id);
  }

  @Override
  public UserEntity addNewBeer(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }

  @Override
  public UserEntity updateBeer(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }
}
