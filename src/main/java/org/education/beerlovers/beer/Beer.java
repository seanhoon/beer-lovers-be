package org.education.beerlovers.beer;

import lombok.*;
import org.education.beerlovers.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "beers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
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
  @Column(name =  "first_brewed", nullable = true)
  private LocalDate first_brewed;
  @Column(name =  "description", nullable = true)
  private String description;
  @Column(name =  "image_url", nullable = true)
  private String image_url;

  @ManyToMany(mappedBy = "beers")
  private List<User> users;

  public Beer(String beerName, Double price, Double score, List<User> users) {
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
}
