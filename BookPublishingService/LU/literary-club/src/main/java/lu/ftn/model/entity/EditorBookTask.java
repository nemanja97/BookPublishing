package lu.ftn.model.entity;

import lu.ftn.model.entity.Book;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class EditorBookTask {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    @Column
    private String taskName;
    @Column
    private String taskId;
    @ManyToOne
    private Book book;
    @ManyToOne
    private User editor;

    public EditorBookTask() {
    }

    public EditorBookTask(String taskName, String taskId, Book book, User editor) {
        this.id = null;
        this.taskName = taskName;
        this.taskId = taskId;
        this.book = book;
        this.editor = editor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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
}
