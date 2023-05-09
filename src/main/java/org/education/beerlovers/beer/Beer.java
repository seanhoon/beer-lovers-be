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
  private String beerName;
  private Double price;
  private Double score;
  private LocalDate first_brewed;
  private String description;
  private String image_url;

  @ManyToMany(mappedBy = "beers")
  private List<User> users;
}
