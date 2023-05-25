package org.education.beerlovers.beer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.education.beerlovers.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "beers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Beer implements Comparable<Beer> {
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
  @Column(name =  "name", nullable = false)
  private String name;
  @Column(name =  "price", nullable = false)
  private Double price;
  @Column(name =  "score", nullable = false)
  private Double score;
  @Column(name =  "first_brewed")
  private String first_brewed;
  @Column(name =  "description")
  private String description;
  @Column(name =  "image_url")
  private String image_url;

  @ManyToMany(mappedBy = "beers")
  @JsonIgnore
  private Set<User> users;

  public Beer(String beerName, Double price, Double score, Set<User> users) {
    this.name = beerName;
    this.price = price;
    this.score = score;
    this.users = users;
  }

  public Beer(String beerName, Double price, Double score) {
    this.name = beerName;
    this.price = price;
    this.score = score;
  }

  @Override
  public int compareTo(Beer o) {
    if (getName() == null || o.getName() == null) {
      return 0;
    }
    return getName().compareTo(o.getName());
  }
}
