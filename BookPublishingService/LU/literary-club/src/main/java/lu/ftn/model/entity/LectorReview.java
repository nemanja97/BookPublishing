package lu.ftn.model.entity;

import javax.persistence.*;

@Entity
public class LectorReview {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User lector;
    @Column
    private String review;
    @Column
    private boolean positive;

    public LectorReview() {
    }

    public LectorReview(Long id, Book book, User lector, String review, boolean positive) {
        this.id = id;
        this.book = book;
        this.lector = lector;
        this.review = review;
        this.positive = positive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        this.lector = lector;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }
}
