package org.education.beerlovers.user;

import lombok.AllArgsConstructor;
import org.education.beerlovers.beer.Beer;
import org.education.beerlovers.beer.BeerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
  private BeerRepository beerRepository;

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
      return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
  }

  public String signUpUser(User user) {
    boolean userExists = userRepository
      .findByEmail(user.getEmail())
      .isPresent();
    if (userExists) {
      throw new IllegalStateException("email already taken");
    }

    String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.setPassword(encodedPassword);
    userRepository.save(user);

    return "user is registered";
  }

  public List<User> fetchUsers() {
    return userRepository.fetchUsers();
  }

  public Boolean doesBeerExistForUser(Long userId, String beerName) {
    User user = userRepository.findById(userId).get();

    Optional<Beer> beer = beerRepository.findBeerByName(beerName);
      if (beer.isPresent()) {
        Long beerId = beer.get().getBeerId();
        Set<Beer> filteredBeerList = user.getBeers()
          .stream()
          .filter(currBeer -> currBeer.getBeerId() == beerId)
          .collect(Collectors.toSet());
        if (filteredBeerList.size() > 0) {
          return true;
        } else {
          return false;
        }
      }
      return false;
  }

  public Boolean addBeerToUser(Long userId, Beer beer) {
    Boolean beerAlreadyExistForUser = doesBeerExistForUser(userId, beer.getBeerName());
    if (beerAlreadyExistForUser) {
      return false;
    }
    User user = userRepository.findById(userId).get();
    Set<Beer> userBeersToUpdate = user.getBeers();
    userBeersToUpdate.add(beer);
    user.setBeers(userBeersToUpdate);
    userRepository.save(user);
    return true;
  }
}
