package org.education.beerlovers.beer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.education.beerlovers.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "beers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beer {
  @Id
  @SequenceGenerator(
    name = "beer_sequence",
    sequenceName = "beer_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "beer_sequence"
  )
  private Long beerId;
  @Column(name =  "beerName", nullable = false)
  private String beerName;
  @Column(name =  "price", nullable = false)
  private Double price;
  @Column(name =  "score", nullable = false)
  private Double score;
  @Column(name =  "first_brewed")
  private LocalDate first_brewed;
  @Column(name =  "description")
  private String description;
  @Column(name =  "image_url")
  private String image_url;

  @ManyToMany(mappedBy = "beers")
  @JsonIgnore
  private Set<User> users;

  public Beer(String beerName, Double price, Double score, Set<User> users) {
    this.beerName = beerName;
    this.price = price;
    this.score = score;
    this.users = users;
  }

  public Beer(String beerName, Double price, Double score) {
    this.beerName = beerName;
    this.price = price;
    this.score = score;
  }

//  public boolean equals(Beer other) {
//    return beerId.equals(other.beerId);
//  }

//  @Override
//  public int hashCode() {
//    int result = getBeerName().hashCode();
//    result = 31 * result + getUsers().hashCode();
//    return result;
//  }
}
