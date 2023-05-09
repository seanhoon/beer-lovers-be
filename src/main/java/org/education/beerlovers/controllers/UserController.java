//package org.education.beerlovers.controllers;
//
//import org.apache.coyote.Response;
//import org.education.beerlovers.entity.UserEntity;
//import org.education.beerlovers.service.UserService;
//import org.education.beerlovers.user.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/v1/user")
//@CrossOrigin(origins="http://localhost:3000")
//public class UserController {
//
//  @Autowired
//  private UserRepository userRepository;
//
//  @GetMapping("/users")
//  public List<UserEntity> getAllUsers() {
//    return userRepository.findAll();
//  }
//
//  @GetMapping("/users/{id}")
//  public ResponseEntity<UserEntity> findById(@PathVariable("id") Long id) throws Exception {
//    UserEntity userEntity = userRepository.findById(id)
//      .orElseThrow(() -> new Exception("User not found"));
//    return ResponseEntity.ok().body(userEntity);
//  }
//
//  @PutMapping("/users/{id}")
//  public ResponseEntity<UserEntity> updateBeer(
//    @PathVariable(value = "id")
//    @RequestBody UserEntity userEntity
//  ) throws Exception {
//    UserEntity user = userRepository.findById(userEntity.getId())
//      .orElseThrow(() -> new Exception("User not found"));
//    user.setBeers(userEntity.getBeers());
//    final UserEntity updatedUser = userRepository.save(user);
//    return ResponseEntity.ok(updatedUser);
//  }
//  @PostMapping
//  public ResponseEntity<UserEntity> addBeer(@RequestBody UserEntity userEntity, String newBeer) throws Exception {
//    UserEntity user = userRepository.findById(userEntity.getId())
//      .orElseThrow(() -> new Exception("User not found"));
//    user.setBeers(userEntity.getBeers());
//    String[] currentBeerList = user.getBeers();
//    if (Arrays.stream(currentBeerList).anyMatch(beer -> beer.equalsIgnoreCase(newBeer))) {
//      throw new Exception("Beer already exists in user's list");
//    } else {
//      Arrays.asList(user.getBeers()).add(newBeer);
//      user.setBeers(user.getBeers());
//      final UserEntity updatedUser = userRepository.save(user);
//      return ResponseEntity.ok(updatedUser);
//    }
//  }
//}
