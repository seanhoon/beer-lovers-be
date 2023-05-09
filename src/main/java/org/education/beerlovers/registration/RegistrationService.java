package org.education.beerlovers.registration;

import lombok.AllArgsConstructor;
import org.education.beerlovers.beer.Beer;
import org.education.beerlovers.user.User;
import org.education.beerlovers.user.UserRole;
import org.education.beerlovers.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

  private final UserService userService;
  private EmailValidator emailValidator;
  public String register(RegistrationRequest request) {
    boolean isValidEmail = emailValidator.test(request.getEmail());
    if(!isValidEmail) {
      throw new IllegalStateException("username not valid");
    }
    List<Beer> beers = List.of();
    List<Long> likedBy = List.of();
    return userService.signUpUser(
      new User(
        request.getEmail(),
        request.getPassword(),
        UserRole.USER,
        likedBy,
        beers
      )
    );
  }
}
