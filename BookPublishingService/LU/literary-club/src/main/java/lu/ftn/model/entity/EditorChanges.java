package lu.ftn.model.entity;

import javax.persistence.*;

@Entity
public class EditorChanges {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Book book;
    @Column
    private String text;

    public EditorChanges() {
    }

    public EditorChanges(Long id, Book book, String text) {
        this.id = id;
        this.book = book;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
