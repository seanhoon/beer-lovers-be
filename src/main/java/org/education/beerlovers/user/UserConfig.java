//package org.education.beerlovers.user;
//
//import org.education.beerlovers.beer.Beer;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class UserConfig {
//
//
//  List<Beer> amyBeerList = List.of(
//    new Beer("corona", 7.0, 8.0),
//    new Beer("asahi", 8.0, 9.0)
//  );
//
//  List<Beer> benBeerList = List.of(
//    new Beer("corona", 7.0, 8.0),
//    new Beer("guiness", 10.0, 9.0)
//  );
//
//  List<Beer> tomBeerList = List.of(
//    new Beer("heineken", 8.0, 7.0)
//  );
//
//  List<Long> likedByList = Collections.emptyList();
//
//  @Bean
//  CommandLineRunner commandLineRunner(UserRepository userRepository) {
//    return args -> {
//      User tom = new User("user1@epam.com", "password", UserRole.USER, likedByList, tomBeerList);
//      User amy = new User("user2@epam.com", "password", UserRole.USER, likedByList, amyBeerList);
//      User ben = new User("user3@epam.com", "password", UserRole.USER, likedByList, benBeerList);
//
//      userRepository.saveAll(List.of(tom, amy, ben));
//    };
//  }
//}
