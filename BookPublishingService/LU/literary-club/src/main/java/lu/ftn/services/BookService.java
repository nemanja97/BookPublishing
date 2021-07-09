package lu.ftn.services;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.User;
import lu.ftn.model.entity.Writer;

import java.util.List;

public interface BookService {

    Book findById(String bookId);

    Book save(Book book);

    List<Book> findAllByEditor(User editor);

    List<Book> findAllByLector(User lector);

    List<Book> findAllByWriter(Writer writer);

    List<Book> findAllByBetaReadersContaining(Reader reader);

    Book findByTitleAndWriterInfo(String title, String writerName, String writerSurname);
}
