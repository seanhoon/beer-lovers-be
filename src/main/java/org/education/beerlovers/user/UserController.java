package org.education.beerlovers.user;

import org.education.beerlovers.beer.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

  private final UserService userService;
  private final UserRepository userRepository;

  @Autowired
  public UserController(UserService userService,
                        UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping("/allUsers")
  public ResponseEntity<Map<String, Object>> fetchUser() {
    final List<User> users = userService.fetchUsers();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("users", users);
    return ResponseEntity.status(200).body(map);
  }

  @PostMapping("/{userId}/addBeer")
  public ResponseEntity<Map<String, Object>> addBeer(@PathVariable Long userId, @RequestBody Beer beer) {
    Boolean isBeerAdded = userService.addBeerToUser(userId, beer);

    User user = userRepository.findById(userId).get();
    Map<String, Object> map = new HashMap<String, Object>();
    if (!isBeerAdded) {
      map.put("error", "beer already exist in user's database");
      map.put("user", user);
      return ResponseEntity.status(400).body(map);
    }
    map.put("msg", "beer has been added into user's list");
    map.put("user", user);
    return ResponseEntity.status(200).body(map);
  }
}
