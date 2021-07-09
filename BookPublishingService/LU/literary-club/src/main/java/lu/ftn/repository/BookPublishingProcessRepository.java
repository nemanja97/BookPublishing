package lu.ftn.repository;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookPublishingProcessRepository extends JpaRepository<BookPublishingProcess, String> {
    BookPublishingProcess findByBook(Book book);

    List<BookPublishingProcess> findAllByEditor(User editor);

    List<BookPublishingProcess> findAllByLector(User lector);

    BookPublishingProcess findByProcessId(String processId);
}
