package org.education.beerlovers.registration;

import lombok.AllArgsConstructor;
import org.education.beerlovers.beer.Beer;
import org.education.beerlovers.user.User;
import org.education.beerlovers.user.UserRole;
import org.education.beerlovers.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RegistrationService {

  private final UserService userService;
  private EmailValidator emailValidator;
  public ResponseEntity<Map<String, Object>> register(RegistrationRequest request) {
    boolean isValidEmail = emailValidator.test(request.getEmail());
    Map<String, Object> map = new HashMap<String, Object>();
    if(!isValidEmail) {
      map.put("error", "username not valid");
      return ResponseEntity.status(400).body(map);
    }
    List<Beer> beers = List.of();
    List<Long> likedBy = List.of();
    userService.signUpUser(
      new User(
        request.getEmail(),
        request.getPassword(),
        UserRole.USER,
        likedBy,
        beers
      )
    );
    map.put("success", "user has been registered");
    return ResponseEntity.status(200).body(map);
  }
}
