package org.education.beerlovers.beer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeerService {

  private final BeerRepository beerRepository;

  @Autowired
  public BeerService(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }
}
