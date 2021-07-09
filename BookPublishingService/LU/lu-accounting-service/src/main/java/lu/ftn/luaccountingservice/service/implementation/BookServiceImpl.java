package lu.ftn.luaccountingservice.service.implementation;

import lu.ftn.luaccountingservice.model.entity.Book;
import lu.ftn.luaccountingservice.repository.BookRepository;
import lu.ftn.luaccountingservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(String id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
