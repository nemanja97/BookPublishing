package rs.ac.uns.ftn.uddproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.uddproject.model.entity.Book;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookResponseDTO {

  private String title;

  private String author;

  private String authorId;

  private String filePath;

  private String coverImageInBase64;

  private boolean openAccess;

  private BigDecimal price;

  private String currency;

  private Set<Genre> genres;

  public BookResponseDTO(Book book) {
    title = book.getTitle();
    author = book.getWriter().getFirstName() + " " + book.getWriter().getLastName();
    authorId = book.getWriter().getId();
    filePath = book.getFilePath();
    coverImageInBase64 = book.getCoverImageInBase64();
    openAccess = book.isOpenAccess();
    price = book.getPrice();
    currency = book.getCurrency();
    genres = book.getGenres();
  }
}
