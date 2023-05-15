package org.education.beerlovers.beer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

  @Query(value = "SELECT * FROM beers WHERE beer_name = ?1", nativeQuery = true)
  Optional<Beer> findBeerByName(String beerName);
}
