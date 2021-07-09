package lu.ftn.repository;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.EditorChanges;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditorChangesRepository extends JpaRepository<EditorChanges, Long> {
    List<EditorChanges> findAllByBook(Book book);
}
