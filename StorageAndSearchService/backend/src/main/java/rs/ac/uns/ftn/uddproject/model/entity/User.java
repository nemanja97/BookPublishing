package rs.ac.uns.ftn.uddproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;
import rs.ac.uns.ftn.uddproject.model.enums.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
    name = "users",
    indexes = {@Index(name = "idx_user_email", columnList = "email")})
public class User {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private String id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String country;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(nullable = false)
  private boolean isActive = false;

  @Column(nullable = false)
  private boolean isBetaReader = false;

  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(
      name = "owned_books",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "book_id")})
  private Set<Book> ownedBooks = new HashSet<>();

  @OneToMany(mappedBy = "writer", cascade = CascadeType.PERSIST)
  private Set<Book> writtenBooks = new HashSet<>();

  @ElementCollection(targetClass = Genre.class)
  @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "genres_interested_in_reading")
  @Enumerated(EnumType.STRING)
  private Set<Genre> genresInterestedInReading = new HashSet<>();

  @ElementCollection(targetClass = Genre.class)
  @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "genres_interested_in_beta_reading")
  @Enumerated(EnumType.STRING)
  private Set<Genre> genresInterestedInBetaReading = new HashSet<>();

  public User(
      String email,
      String password,
      String firstName,
      String lastName,
      String city,
      String country,
      Role role,
      boolean isActive,
      boolean isBetaReader) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.city = city;
    this.country = country;
    this.role = role;
    this.isActive = isActive;
    this.isBetaReader = isBetaReader;
  }
}
