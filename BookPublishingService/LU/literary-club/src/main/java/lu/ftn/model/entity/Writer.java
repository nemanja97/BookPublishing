package lu.ftn.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Writer extends User {

    @ElementCollection(targetClass = Genre.class)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interests", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Genre> genresInterestedIn = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private Set<Book> writtenBooks = new HashSet<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private Set<MembershipProcess> membershipProcesses = new HashSet<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean accepted = false;

    public Writer() {
    }

    public Writer(String id, String name, String surname, String city, String country, String email, String username, String password, Role role, boolean active, List<Genre> genresInterestedIn, Set<Book> writtenBooks, Set<MembershipProcess> membershipProcesses, boolean accepted) {
        super(id, name, surname, city, country, email, username, password, role, active);
        this.genresInterestedIn = genresInterestedIn;
        this.writtenBooks = writtenBooks;
        this.membershipProcesses = membershipProcesses;
        this.accepted = accepted;
    }

    public Writer(String id, String name, String surname, String city, String country, String email, String username, String password, List<Genre> genresInterestedIn, Set<Book> writtenBooks, Set<MembershipProcess> membershipProcesses) {
        super(id, name, surname, city, country, email, username, password, Role.ROLE_WRITER, false);
        this.genresInterestedIn = genresInterestedIn;
        this.writtenBooks = writtenBooks;
        this.membershipProcesses = membershipProcesses;
        this.accepted = false;
    }

    public List<Genre> getGenresInterestedIn() {
        return genresInterestedIn;
    }

    public void setGenresInterestedIn(List<Genre> genresInterestedIn) {
        this.genresInterestedIn = genresInterestedIn;
    }

    public Set<Book> getWrittenBooks() {
        return writtenBooks;
    }

    public void setWrittenBooks(Set<Book> writtenBooks) {
        this.writtenBooks = writtenBooks;
    }

    public Set<MembershipProcess> getMembershipProcesses() {
        return membershipProcesses;
    }

    public void setMembershipProcesses(Set<MembershipProcess> membershipProcesses) {
        this.membershipProcesses = membershipProcesses;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
