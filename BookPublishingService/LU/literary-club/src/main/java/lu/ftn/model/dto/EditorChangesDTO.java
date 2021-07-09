package lu.ftn.model.dto;

public class EditorChangesDTO {
    private String bookId;
    private String comment;

    public EditorChangesDTO(String bookId, String comment) {
        this.bookId = bookId;
        this.comment = comment;
    }

    public EditorChangesDTO() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
