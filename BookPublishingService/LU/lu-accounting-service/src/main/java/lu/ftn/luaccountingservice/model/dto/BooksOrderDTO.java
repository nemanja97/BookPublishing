package lu.ftn.luaccountingservice.model.dto;

import java.util.List;

public class BooksOrderDTO {

    private List<String> bookIds;

    public BooksOrderDTO() {
    }

    public BooksOrderDTO(List<String> bookIds) {
        this.bookIds = bookIds;
    }

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }
}
