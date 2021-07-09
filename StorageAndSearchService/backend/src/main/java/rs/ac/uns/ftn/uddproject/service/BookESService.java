package rs.ac.uns.ftn.uddproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.BookES;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;

import java.io.IOException;
import java.util.List;

public interface BookESService {

  SearchPage<BookES> findBooks(
      String query,
      String title,
      String author,
      String contentExcerpt,
      List<Genre> genres,
      Pageable pageable);

  String parsePDF(String filePath) throws IOException;

  BookES save(BookES bookES);

  BookES findById(String id);

  Page<BookES> findByTitleLike(String query, Pageable pageable);

  Page<BookES> findByContentContains(String query, Pageable pageable);

  Page<BookES> findByGenresContaining(String query, Pageable pageable);
}
