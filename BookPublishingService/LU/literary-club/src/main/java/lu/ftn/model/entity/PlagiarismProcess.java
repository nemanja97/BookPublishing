package lu.ftn.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PlagiarismProcess {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blamed_user_id")
    private Writer blamedWriter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "blamed_book_id")
    private Book blamedBook;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "original_user_id")
    private Writer originalWriter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "original_book_id")
    private Book originalBook;

    @OneToMany(mappedBy = "plagiarismProcess", cascade = CascadeType.ALL)
    private Set<PlagiarismProcessNote> notes = new HashSet<>();

    @OneToMany(mappedBy = "plagiarismProcess", cascade = CascadeType.ALL)
    private Set<PlagiarismProcessVote> votes = new HashSet<>();

    @Column(name = "decision", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlagiarismDecision decision = PlagiarismDecision.UNDECIDED;

    public PlagiarismProcess() {
    }

    public PlagiarismProcess(String id, Writer blamedWriter, Book blamedBook, Writer originalWriter, Book originalBook, Set<PlagiarismProcessNote> notes, Set<PlagiarismProcessVote> votes, PlagiarismDecision decision) {
        this.id = id;
        this.blamedWriter = blamedWriter;
        this.blamedBook = blamedBook;
        this.originalWriter = originalWriter;
        this.originalBook = originalBook;
        this.notes = notes;
        this.votes = votes;
        this.decision = decision;
    }

    public PlagiarismProcess(Writer blamedWriter, Book blamedBook, Writer originalWriter, Book originalBook) {
        this.blamedWriter = blamedWriter;
        this.blamedBook = blamedBook;
        this.originalWriter = originalWriter;
        this.originalBook = originalBook;
        this.notes = new HashSet<>();
        this.votes = new HashSet<>();
        this.decision = PlagiarismDecision.UNDECIDED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Writer getBlamedWriter() {
        return blamedWriter;
    }

    public void setBlamedWriter(Writer blamedWriter) {
        this.blamedWriter = blamedWriter;
    }

    public Book getBlamedBook() {
        return blamedBook;
    }

    public void setBlamedBook(Book blamedBook) {
        this.blamedBook = blamedBook;
    }

    public Writer getOriginalWriter() {
        return originalWriter;
    }

    public void setOriginalWriter(Writer originalWriter) {
        this.originalWriter = originalWriter;
    }

    public Book getOriginalBook() {
        return originalBook;
    }

    public void setOriginalBook(Book originalBook) {
        this.originalBook = originalBook;
    }

    public Set<PlagiarismProcessNote> getNotes() {
        return notes;
    }

    public void setNotes(Set<PlagiarismProcessNote> notes) {
        this.notes = notes;
    }

    public Set<PlagiarismProcessVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<PlagiarismProcessVote> votes) {
        this.votes = votes;
    }

    public PlagiarismDecision getDecision() {
        return decision;
    }

    public void setDecision(PlagiarismDecision decision) {
        this.decision = decision;
    }
}
