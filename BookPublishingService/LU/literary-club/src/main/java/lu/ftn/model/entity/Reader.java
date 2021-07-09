package lu.ftn.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Reader extends User {

    @ElementCollection(targetClass = Genre.class)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interests", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Genre> genresInterestedIn = new ArrayList<>();

    @ElementCollection(targetClass = Genre.class)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "betaGenre_interests", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<Genre> betaGenresInterestedIn = new ArrayList<>();

    @Column
    private Integer strikes;

    @ManyToMany(mappedBy = "betaReaders")
    private Set<Book> reviewing;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean betaReader = false;

    public Reader() {
    }

    public Reader(String id, String name, String surname, String city, String country, String email, String username, String password, Role role, boolean active, List<Genre> genresInterestedIn, List<Genre> betaGenresInterestedIn, boolean betaReader) {
        super(id, name, surname, city, country, email, username, password, role, active);
        this.genresInterestedIn = genresInterestedIn;
        this.betaGenresInterestedIn = betaGenresInterestedIn;
        this.betaReader = betaReader;
        this.strikes = 0;
    }
    public Reader(String id, String name, String surname, String city, String country, String email, String username, String password, List<Genre> genresInterestedIn, List<Genre> betaGenresInterestedIn, boolean betaReader) {
        super(id, name, surname, city, country, email, username, password, Role.ROLE_READER, false);
        this.genresInterestedIn = genresInterestedIn;
        this.betaGenresInterestedIn = betaGenresInterestedIn;
        this.betaReader = betaReader;
        this.strikes = 0;
    }



    public List<Genre> getGenresInterestedIn() {
        return genresInterestedIn;
    }

    public void setGenresInterestedIn(List<Genre> genresInterestedIn) {
        this.genresInterestedIn = genresInterestedIn;
    }

    public List<Genre> getBetaGenresInterestedIn() {
        return betaGenresInterestedIn;
    }

    public void setBetaGenresInterestedIn(List<Genre> betaGenresInterestedIn) {
        this.betaGenresInterestedIn = betaGenresInterestedIn;
    }

    public boolean isBetaReader() {
        return betaReader;
    }

    public void setBetaReader(boolean betaReader) {
        this.betaReader = betaReader;
    }

    public Integer getStrikes() {
        return strikes;
    }

    public void setStrikes(Integer strikes) {
        this.strikes = strikes;
    }

    public Set<Book> getReviewing() {
        return reviewing;
    }

    public void setReviewing(Set<Book> reviewing) {
        this.reviewing = reviewing;
    }
}
