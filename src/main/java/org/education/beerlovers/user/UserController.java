package org.education.beerlovers.user;

import org.education.beerlovers.beer.Beer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/v1/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/allUsers")
  public ResponseEntity<Map<String, Object>> fetchUser() {
    final List<User> users = userService.fetchUsers();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("users", users);
    return ResponseEntity.status(200).body(map);
  }
}
