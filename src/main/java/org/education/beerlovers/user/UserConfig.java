package org.education.beerlovers.user;

import org.education.beerlovers.beer.Beer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class UserConfig {

  List<Long> emptyLikedByList = Collections.emptyList();

  private Beer makeBeer(String beerName, Double price, Double score) {
    return new Beer(beerName, price, score);
  };

  @Bean
  CommandLineRunner commandLineRunner(UserRepository userRepository) {
    Set<Beer> tomBeerList = new HashSet<Beer>();
    Set<Beer> amyBeerList = new HashSet<Beer>();
    Set<Beer> benBeerList = new HashSet<Beer>();
    tomBeerList.add(makeBeer("heineken", 6.0, 6.5));
    tomBeerList.add(makeBeer("asahi", 7.5, 8.5));
    amyBeerList.add(makeBeer("corona", 7.0, 8.0));
    amyBeerList.add(makeBeer("guiness", 10.0, 9.0));
    benBeerList.add(makeBeer("asahi", 7.0, 8.5));

    return args -> {
      User tom = new User("user1@epam.com", "password", UserRole.ADMIN, emptyLikedByList, tomBeerList);
      User amy = new User("user2@epam.com", "password", UserRole.USER, emptyLikedByList, amyBeerList);
      User ben = new User("user3@epam.com", "password", UserRole.USER, emptyLikedByList, benBeerList);

      userRepository.saveAll(List.of(tom, amy, ben));
    };
  }
}
