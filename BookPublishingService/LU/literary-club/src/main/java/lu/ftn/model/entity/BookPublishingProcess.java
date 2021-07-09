package lu.ftn.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class BookPublishingProcess {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column(unique = true)
    private String processId;
    @OneToOne
    private Book book;
    @ManyToOne
    private User editor;
    @ManyToOne
    private User writer;
    @ManyToOne
    private User lector;

    public BookPublishingProcess() {
    }

    public BookPublishingProcess(String processId, Book book, User editor, User writer) {
        this.id = null;
        this.processId = processId;
        this.book = book;
        this.editor = editor;
        this.writer = writer;
        this.lector = null;
    }

    public BookPublishingProcess(String id, String processId, Book book, User editor, Writer writer, User lector) {
        this.id = id;
        this.processId = processId;
        this.book = book;
        this.editor = editor;
        this.writer = writer;
        this.lector = lector;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        this.lector = lector;
    }
}
