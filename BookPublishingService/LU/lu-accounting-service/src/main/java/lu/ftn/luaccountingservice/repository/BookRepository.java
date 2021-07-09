package lu.ftn.luaccountingservice.repository;

import lu.ftn.luaccountingservice.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
