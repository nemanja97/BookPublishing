package rs.ac.uns.ftn.uddproject.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.uddproject.model.entity.Book;

import javax.transaction.Transactional;

@Transactional
public interface BookRepository extends JpaRepository<Book, String> {}
