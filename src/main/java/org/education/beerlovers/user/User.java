package org.education.beerlovers.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.education.beerlovers.beer.Beer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

//  @ManyToMany(cascade = {CascadeType.ALL})
  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JsonIgnore
  @JoinTable(
    name = "user_beers",
    joinColumns = @JoinColumn(name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "beerId")
  )
  private Set<Beer> beers = new HashSet<>();

  public User(String email, String password, UserRole role, List<Long> likedBy, Set<Beer> beers) {
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

//  public boolean equals(User other) {
//    return userId.equals(other.userId);
//  }

//  @Override
//  public int hashCode() {
//    int result = getUserId().hashCode();
//    result = 31 * result + getBeers().hashCode();
//    result = 31 * result + getEmail().hashCode();
//    return result;
//  }
}
