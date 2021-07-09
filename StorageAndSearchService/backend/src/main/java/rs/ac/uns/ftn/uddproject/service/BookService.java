package rs.ac.uns.ftn.uddproject.service;

import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uddproject.exception.PlagiarismException;
import rs.ac.uns.ftn.uddproject.model.entity.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {

  List<Book> findAll();

  Book findById(String id);

  Book save(Book book);

  Book save(Book book, MultipartFile file) throws IOException, PlagiarismException;
}
