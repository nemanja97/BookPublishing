package lu.ftn.luaccountingservice.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    private Set<Book> ownedBooks = new HashSet<>();

    @ManyToMany
    private Set<Membership> memberships = new HashSet<>();

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_READER;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Set<Book> ownedBooks) {
        this.email = email;
        this.password = password;
        this.ownedBooks = ownedBooks;
    }

    public User(String id, String email, String password, Set<Book> ownedBooks, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.ownedBooks = ownedBooks;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Book> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(Set<Book> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(Set<Membership> memberships) {
        this.memberships = memberships;
    }
}
