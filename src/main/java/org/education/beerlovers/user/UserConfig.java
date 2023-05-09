package org.education.beerlovers.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

  String[] tomBeerList = new String[] {"Heineken", "Tiger"};
  String[] amyBeerList = new String[] {"Corona", "Asahi"};
  String[] benBeerList = new String[] {"Anchor", "Tiger"};

//  @Bean
//  CommandLineRunner commandLineRunner(UserRepository userRepository) {
//    return args -> {
//      User tom = new User("Tom", tomBeerList, new String[] {}, new String[] {});
//      User amy = new User("amy", amyBeerList, new String[] {}, new String[] {});
//      User ben = new User("ben", benBeerList, new String[] {}, new String[] {});
//
//      userRepository.saveAll(List.of(tom, amy, ben));
//    };
//  }
}
