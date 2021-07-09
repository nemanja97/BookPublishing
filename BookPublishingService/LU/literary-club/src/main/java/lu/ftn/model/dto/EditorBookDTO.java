package lu.ftn.model.dto;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookStatus;

public class EditorBookDTO {

    private String id;
    private String title;
    private String author;
    private String synopsis;
    private BookStatus status;
    private String taskId;
    private String taskName;

    public EditorBookDTO() {
    }

    public EditorBookDTO(Book book, String taskId, String taskName) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getWriter().getName() + " " + book.getWriter().getSurname();
        this.status = book.getBookStatus();
        this.synopsis = book.getSynopsis();
        this.taskId = taskId;
        this.taskName = taskName;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
