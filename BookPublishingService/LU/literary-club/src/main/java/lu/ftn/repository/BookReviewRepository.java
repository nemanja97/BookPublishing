package lu.ftn.repository;

import lu.ftn.model.entity.BetaReaderReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BetaReaderReview, Long> {
}
