package lu.ftn.model.entity;

import javax.persistence.*;

@Entity
public class BetaReaderReview {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Reader betaReader;

    @Column
    private String reviewText;

    public BetaReaderReview() {
    }

    public BetaReaderReview(Long id, Book book, Reader betaReader, String reviewText) {
        this.id = id;
        this.book = book;
        this.betaReader = betaReader;
        this.reviewText = reviewText;
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

    public Reader getBetaReader() {
        return betaReader;
    }

    public void setBetaReader(Reader betaReader) {
        this.betaReader = betaReader;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
