package org.education.beerlovers.user;

import org.education.beerlovers.beer.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  @Query(value = "SELECT * FROM users WHERE users.user_id != ?1", nativeQuery = true)
  List<User> fetchFriends(Long userId);
}
