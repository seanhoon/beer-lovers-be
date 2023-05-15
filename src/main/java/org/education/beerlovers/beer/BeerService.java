package org.education.beerlovers.beer;

import org.education.beerlovers.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BeerService {

  private final BeerRepository beerRepository;

  @Autowired
  public BeerService(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  public Beer addBeer(Beer beer) {
    return beerRepository.save(beer);
  }

  public Optional<Beer> findBeerByName(String beerName) {
    return beerRepository.findBeerByName(beerName);
  }
}
