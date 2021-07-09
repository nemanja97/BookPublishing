package lu.ftn.services.implementation;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.User;
import lu.ftn.model.entity.Writer;
import lu.ftn.repository.BookRepository;
import lu.ftn.services.BookService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public Book findByTitleAndWriterInfo(String title, String writerName, String writerSurname) {
        return bookRepository.findByTitleAndWriterNameAndWriterSurname(title, writerName, writerSurname);
    }
    @Override
    public Book findById(String bookId) {
        return bookRepository.findById(bookId).get();
    }
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAllByEditor(User editor) {
        return bookRepository.findAllByEditor(editor);
    }

    @Override
    public List<Book> findAllByLector(User lector) {
        return bookRepository.findAllByLector(lector);
    }

    @Override
    public List<Book> findAllByWriter(Writer writer) {
        return bookRepository.findAllByWriter(writer);
    }

    @Override
    public List<Book> findAllByBetaReadersContaining(Reader reader) {
        return bookRepository.findAllByBetaReadersContaining(reader);
    }
}
