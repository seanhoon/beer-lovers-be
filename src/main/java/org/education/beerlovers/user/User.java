package org.education.beerlovers.user;

import lombok.*;
import org.education.beerlovers.beer.Beer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

  @Id
  @SequenceGenerator(
    name = "user_sequence",
    sequenceName = "user_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "user_sequence"
  )
  private Long userId;
  @Column(name =  "email")
  private String email;
  @Column(name =  "password")
  private String password;
  @Column(name =  "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;
  @Column(name = "likedBy")
  @ElementCollection(targetClass = String.class)
  private List<Long> likedBy;

  @ManyToMany
  @JoinTable(
    name = "user_beers",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "beerId")
  )
  private List<Beer> beers;

  public User(String email, String password, UserRole role, List<Long> likedBy, List<Beer> beers) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.likedBy = likedBy;
    this.beers = beers;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
    return Collections.singletonList(authority);
  }
}
