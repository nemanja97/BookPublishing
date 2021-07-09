package lu.ftn.repository;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.LectorReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectorReviewRepository extends JpaRepository<LectorReview, Long> {
    List<LectorReview> findLectorReviewsByBook(Book book);
}
