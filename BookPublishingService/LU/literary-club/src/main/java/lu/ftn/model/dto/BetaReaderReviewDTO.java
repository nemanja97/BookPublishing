package lu.ftn.model.dto;

import lu.ftn.model.entity.BetaReaderReview;


public class BetaReaderReviewDTO {

    private Long id;
    private String betaReader;
    private String reviewText;

    public BetaReaderReviewDTO() {
    }

    public BetaReaderReviewDTO(Long id, String betaReader, String reviewText) {
        this.id = id;
        this.betaReader = betaReader;
        this.reviewText = reviewText;
    }

    public BetaReaderReviewDTO(BetaReaderReview review){
        this.id = review.getId();
        this.betaReader = review.getBetaReader().getUsername();
        this.reviewText = review.getReviewText();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBetaReader() {
        return betaReader;
    }

    public void setBetaReader(String betaReader) {
        this.betaReader = betaReader;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
