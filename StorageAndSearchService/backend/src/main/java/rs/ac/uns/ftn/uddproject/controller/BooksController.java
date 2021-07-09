package rs.ac.uns.ftn.uddproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uddproject.model.dto.BookInfoDTO;
import rs.ac.uns.ftn.uddproject.model.dto.BookResponseDTO;
import rs.ac.uns.ftn.uddproject.model.entity.Book;
import rs.ac.uns.ftn.uddproject.model.entity.User;
import rs.ac.uns.ftn.uddproject.model.enums.Role;
import rs.ac.uns.ftn.uddproject.service.BookESService;
import rs.ac.uns.ftn.uddproject.service.BookService;
import rs.ac.uns.ftn.uddproject.service.UserESService;
import rs.ac.uns.ftn.uddproject.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static rs.ac.uns.ftn.uddproject.util.ConversionHelper.convertRequestObjectToBookInfoDTO;

@RestController
@RequestMapping(value = "/api/books")
@CrossOrigin
public class BooksController {

  @Autowired BookService bookService;

  @Autowired UserService userService;

  @Autowired BookESService bookESService;

  @Autowired UserESService userESService;

  private static String getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

  @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getBookById(@PathVariable String id) {
    Book book = bookService.findById(id);
    return new ResponseEntity<>(new BookResponseDTO(book), HttpStatus.OK);
  }

  @GetMapping(value = "{id}/download", produces = MediaType.APPLICATION_PDF_VALUE)
  public ResponseEntity<?> downloadBookById(@PathVariable String id, HttpServletResponse response) {
    Book book = bookService.findById(id);
    User user = userService.findUserById(BooksController.getCurrentUserId());
    if (!(book.isOpenAccess() || (user != null && user.getOwnedBooks().contains(book))))
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    try {
      File file = new File(book.getFilePath());
      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

      HttpHeaders header = new HttpHeaders();
      header.add(
          HttpHeaders.CONTENT_DISPOSITION,
          new String(
              String.format("attachment; filename=%s.pdf", book.getTitle())
                  .getBytes(StandardCharsets.UTF_8),
              StandardCharsets.UTF_8));
      header.add("Cache-Control", "no-cache, no-store, must-revalidate");
      header.add("Pragma", "no-cache");
      header.add("Expires", "0");

      return ResponseEntity.ok().headers(header).contentLength(file.length()).body(resource);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @RolesAllowed("WRITER")
  public ResponseEntity<?> addBook(
      @RequestPart(value = "book", required = false) MultipartFile book_file,
      @RequestPart(value = "book_info", required = false) String book_info) {
    if ((book_info == null || book_info.isEmpty()) && (book_file == null))
      return new ResponseEntity<>("Empty payload", HttpStatus.BAD_REQUEST);

    try {
      BookInfoDTO bookInfoDTO = convertRequestObjectToBookInfoDTO(book_info);
      User writer = userService.findUserById(BooksController.getCurrentUserId());
      if (!writer.isActive() || !writer.getRole().equals(Role.ROLE_WRITER))
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

      Book book =
          new Book(
              bookInfoDTO.getTitle(),
              bookInfoDTO.getCoverImageInBase64(),
              bookInfoDTO.getGenres(),
              writer,
              bookInfoDTO.getPrice());
      bookService.save(book, book_file);
    } catch (JsonProcessingException e) {
      return new ResponseEntity<>("Request badly formatted", HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
