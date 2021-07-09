package lu.ftn.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class PlagiarismProcessVote {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User editor;

    @Column(name = "vote", nullable = false)
    @Enumerated(EnumType.STRING)
    private PlagiarismVote vote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plagiarismProcess_id")
    private PlagiarismProcess plagiarismProcess;

    public PlagiarismProcessVote() {
    }

    public PlagiarismProcessVote(String id, User editor, PlagiarismVote vote, PlagiarismProcess plagiarismProcess) {
        this.id = id;
        this.editor = editor;
        this.vote = vote;
        this.plagiarismProcess = plagiarismProcess;
    }

    public PlagiarismProcessVote(User editor, PlagiarismVote vote, PlagiarismProcess plagiarismProcess) {
        this.editor = editor;
        this.vote = vote;
        this.plagiarismProcess = plagiarismProcess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public PlagiarismVote getVote() {
        return vote;
    }

    public void setVote(PlagiarismVote vote) {
        this.vote = vote;
    }

    public PlagiarismProcess getPlagiarismProcess() {
        return plagiarismProcess;
    }

    public void setPlagiarismProcess(PlagiarismProcess plagiarismProcess) {
        this.plagiarismProcess = plagiarismProcess;
    }
}
