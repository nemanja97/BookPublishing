package lu.ftn.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class PlagiarismProcessNote {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User editor;

    @Column(name = "note", nullable = false)
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plagiarismProcess_id")
    private PlagiarismProcess plagiarismProcess;

    public PlagiarismProcessNote() {
    }

    public PlagiarismProcessNote(String id, User editor, String note, PlagiarismProcess plagiarismProcess) {
        this.id = id;
        this.editor = editor;
        this.note = note;
        this.plagiarismProcess = plagiarismProcess;
    }

    public PlagiarismProcessNote(User editor, String note, PlagiarismProcess plagiarismProcess) {
        this.editor = editor;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public PlagiarismProcess getPlagiarismProcess() {
        return plagiarismProcess;
    }

    public void setPlagiarismProcess(PlagiarismProcess plagiarismProcess) {
        this.plagiarismProcess = plagiarismProcess;
    }
}
