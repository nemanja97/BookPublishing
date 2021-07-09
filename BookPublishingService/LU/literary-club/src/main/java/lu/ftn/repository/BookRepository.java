package lu.ftn.repository;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.User;
import lu.ftn.model.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findAllByEditor(User editor);

    List<Book> findAllByLector(User lector);

    List<Book> findAllByWriter(Writer writer);

    List<Book> findAllByBetaReadersContaining(Reader betareader);
    
    Book findByTitleAndWriterNameAndWriterSurname(String title, String name, String surname);

}
