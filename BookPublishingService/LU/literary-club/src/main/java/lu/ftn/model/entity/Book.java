package lu.ftn.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Writer writer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "genres", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres  = new HashSet<>();

    @Column()
    private String filePath;

    @Column()
    private boolean openAccess;

    private BigDecimal price;

    private String currency;


    @Column(unique = true)
    private String isbn;

    @ManyToMany
    private Set<KeyWord> keyWords;

    @Column
    private String publisher;

    @Column
    private String cityOfPublishing;

    @Column
    private Integer numberOfPages;

    @Column
    private Integer year;

    @Column
    private String synopsis;

    @ManyToOne
    private User editor;

    @ManyToOne
    private User lector;

    @ManyToMany
    private Set<Reader> betaReaders;

    @OneToMany(mappedBy = "book")
    private Set<BetaReaderReview> reviews;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    
    @Column(name = "vote", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.IN_REVIEW;

    public Book() {
    }

    public Book(String title, Set<Genre> genres, String synopsis, Writer writer) {
        this.id = null;
        this.title = title;
        this.genres = genres;
        this.synopsis = synopsis;
        this.writer = writer;
        this.editor = null;
        this.lector = null;
        this.filePath = "";
        this.openAccess = true;
    }

    public Book(String id, String title, Writer writer, Set<Genre> genres, String filePath, boolean openAccess, BigDecimal price, String currency) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.genres = genres;
        this.filePath = filePath;
        this.openAccess = openAccess;
        this.price = price;
        this.currency = currency;
        this.status = BookStatus.IN_REVIEW;
    }

    public Book(String id, String title, Writer writer, Set<Genre> genres, String filePath, boolean openAccess, BigDecimal price, String currency, BookStatus status) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.genres = genres;
        this.filePath = filePath;
        this.openAccess = openAccess;
        this.price = price;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Set<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(Set<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCityOfPublishing() {
        return cityOfPublishing;
    }

    public void setCityOfPublishing(String cityOfPublishing) {
        this.cityOfPublishing = cityOfPublishing;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        this.lector = lector;
    }

    public Set<Reader> getBetaReaders() {
        return betaReaders;
    }

    public void setBetaReaders(Set<Reader> betaReaders) {
        this.betaReaders = betaReaders;
    }

    public Set<BetaReaderReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<BetaReaderReview> reviews) {
        this.reviews = reviews;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isOpenAccess() {
        return openAccess;
    }

    public void setOpenAccess(boolean openAccess) {
        this.openAccess = openAccess;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
}
