package lu.ftn.luaccountingservice.model.dto;

import java.util.List;

public class BookListDTO {

    private List<BookDTO> books;

    public BookListDTO() {
    }

    public BookListDTO(List<BookDTO> books) {
        this.books = books;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
