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

  public List<User> fetchFriends(Long userId) {
    return userRepository.fetchFriends(userId);
  }

  private Boolean doesBeerExistForUser(Long userId, String beerName) {
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

  private Boolean doesBeerIdExistForUser(Long userId, Long beerId) {
    User user = userRepository.findById(userId).get();

    Optional<Beer> beer = beerRepository.findById(beerId);
    if (beer.isPresent()) {
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
    Boolean beerAlreadyExistForUser = doesBeerExistForUser(userId, beer.getName());
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

  public Boolean updateUserBeer(Long userId, Beer updatedBeer) {
    Boolean beerAlreadyExistForUser = doesBeerExistForUser(userId, updatedBeer.getName());
    if (!beerAlreadyExistForUser) {
      return false;
    }
    User user = userRepository.findById(userId).get();
    Set<Beer> beerList = user.getBeers();
    Set<Beer> updatedUserBeers = beerList
      .stream()
      .map(beer -> {
        if (beer.getName().equalsIgnoreCase(updatedBeer.getName())) {
          beer = updatedBeer;
        }
        return beer;
      }).collect(Collectors.toSet());
    user.setBeers(updatedUserBeers);
    userRepository.save(user);
    return true;

    // issue 1: this adds a new instance of a beer to the beer table. it doesn't update the row instead.
    // issue 2: user_beers table's original beer id has been updated, instead of just its name/price/etc
  }

  public Boolean deleteUserBeer(Long userId, Long beerId) {
    Boolean beerDoesNotExistForUser = doesBeerIdExistForUser(userId, beerId);
    if (!beerDoesNotExistForUser) {
      return false;
    }
    User user = userRepository.findById(userId).get();
    Optional<Beer> beer = beerRepository.findById(beerId);
    Set<Beer> userBeersToUpdate = user.getBeers();
    userBeersToUpdate.remove(beer);
    user.setBeers(userBeersToUpdate);
    userRepository.save(user);
    return true;
  }

  public Boolean likeFriendBeers(Long userId, Long friendId) {
    Boolean likeStatus;
    User friend = userRepository.findById(friendId).get();
    List<Long> likedByList = friend.getLikedBy();

    if (likedByList.contains(userId)) {
      likedByList = likedByList.stream()
        .filter(personId -> !personId.equals(userId))
        .collect(Collectors.toList());
      likeStatus = false;
    } else {
      likedByList.add(userId);
      likeStatus = true;
    }
    friend.setLikedBy(likedByList);
    userRepository.save(friend);
    return likeStatus;
  }
}
