package org.education.beerlovers.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.mapping.Array;
import org.hibernate.mapping.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name =  "user_id")
  private Long id;

  @Column(name =  "user_name", nullable = false)
  @Size(max = 20)
  private String name;
  @Column(name =  "beers")
  private String[] beers;
}

