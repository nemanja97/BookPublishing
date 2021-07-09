package rs.ac.uns.ftn.uddproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.UserES;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;
import rs.ac.uns.ftn.uddproject.service.BookESService;
import rs.ac.uns.ftn.uddproject.service.UserESService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/search")
@CrossOrigin
public class SearchController {

  @Autowired ElasticsearchOperations elasticsearchOperations;

  @Autowired UserESService userESService;

  @Autowired BookESService bookESService;

  @GetMapping(path = "books", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> searchForBooks(
      @RequestParam(required = false) String query,
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String author,
      @RequestParam(required = false) String contentExcerpt,
      @RequestParam(required = false) List<Genre> genres,
      Pageable pageable) {
    return new ResponseEntity<>(
        bookESService.findBooks(query, title, author, contentExcerpt, genres, pageable),
        HttpStatus.OK);
  }

  @GetMapping(path = "appropriateReviewers", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SearchPage<UserES>> searchForReviewers(
      @RequestParam String bookId, Pageable pageable) {
    return new ResponseEntity<>(
        userESService.findReviewersForBook(bookId, pageable), HttpStatus.OK);
  }
}
