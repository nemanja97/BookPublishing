package lu.ftn.luaccountingservice.service;

import lu.ftn.luaccountingservice.model.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(String id);

    Book save(Book book);
}
