package org.education.beerlovers.user;

import org.education.beerlovers.beer.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


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

  @GetMapping("/{userId}/allFriends")
  public ResponseEntity<Map<String, Object>> fetchFriends(@PathVariable Long userId) {
    final List<User> users = userService.fetchFriends(userId);
    final List<Map<String, Object>> updatedUsers = users.stream()
      .map(user ->  {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", user.getUserId());
        map.put("name", user.getUsername());
        map.put("beers", user.getBeers());
        map.put("likedBy", user.getLikedBy());
        return map;
      }).collect(Collectors.toList());

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("users", updatedUsers);
    return ResponseEntity.status(200).body(map);
  }

  @GetMapping("/{userId}/getBeers")
  public ResponseEntity<Map<String, Object>> fetchBeers(@PathVariable Long userId) {
    final Optional<User> user = userRepository.findById(userId);
    Map<String, Object> map = new HashMap<String, Object>();
    if (user.isPresent()) {
      List<Beer> sortedBeers = user.get().getBeers().stream().collect(Collectors.toList());
      Collections.sort(sortedBeers);
      map.put("beers", sortedBeers);
      return ResponseEntity.status(200).body(map);
    }
    map.put("error", "user does not exist");
    return ResponseEntity.status(400).body(map);
  }

  @PostMapping("/{userId}/addBeer")
  public ResponseEntity<Map<String, Object>> addBeer(@PathVariable Long userId, @RequestBody Beer beer) {
    Boolean isBeerAdded = userService.addBeerToUser(userId, beer);

    Set<Beer> beerList = userRepository.findById(userId).get().getBeers();
    Map<String, Object> map = new HashMap<String, Object>();
    if (!isBeerAdded) {
      map.put("error", "beer already exist in user's database");
      map.put("beers", beerList);
      return ResponseEntity.status(400).body(map);
    }
    map.put("msg", "beer has been added into user's list");
    map.put("beers", beerList);
    return ResponseEntity.status(200).body(map);
  }

  @PutMapping("/{userId}/updateBeer")
  public ResponseEntity<Map<String, Object>> updateUserBeer(@PathVariable Long userId, @RequestBody Beer beer) {
    Boolean isBeerUpdated = userService.updateUserBeer(userId, beer);

    Set<Beer> beerList = userRepository.findById(userId).get().getBeers();
    Map<String, Object> map = new HashMap<String, Object>();
    if (!isBeerUpdated) {
      map.put("error", "beer does not exist in user's database");
      map.put("beers", beerList);
      return ResponseEntity.status(400).body(map);
    }
    map.put("msg", "beer has been updated");
    map.put("beers", beerList);
    return ResponseEntity.status(200).body(map);
  }

  @DeleteMapping("/{userId}/deleteBeer")
  public ResponseEntity<Map<String, Object>> deleteBeer(@PathVariable Long userId, @RequestBody Long beerId) {
    Boolean isBeerDeleted = userService.deleteUserBeer(userId, beerId);

    Set<Beer> beerList = userRepository.findById(userId).get().getBeers();
    Map<String, Object> map = new HashMap<String, Object>();
    if (!isBeerDeleted) {
      map.put("error", "beer does not exist in user's database");
      map.put("beers", beerList);
      return ResponseEntity.status(400).body(map);
    }
    map.put("msg", "beer has been deleted from user's list");
    map.put("beers", beerList);
    return ResponseEntity.status(200).body(map);
  }

  @PutMapping("/{userId}/likedFriendBeers/{friendId}")
  public ResponseEntity<Map<String, Object>> updateLikedBy(@PathVariable Long userId, @PathVariable Long friendId) {
    Boolean likeSuccess = userService.likeFriendBeers(userId, friendId);
    List<Long> likedByList = userRepository.findById(friendId).get().getLikedBy();

    Map<String, Object> map = new HashMap<String, Object>();

    if (likeSuccess) {
      map.put("msg", "current user id: " + userId + " has been added to friend's liked by list");
    } else {
      map.put("msg", "current user id: " + userId + " has been removed from friend's liked by list");
    }

    map.put("likedByList", likedByList);
    return ResponseEntity.status(200).body(map);
  }
}
