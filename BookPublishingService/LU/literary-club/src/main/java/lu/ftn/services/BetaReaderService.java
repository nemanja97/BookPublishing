package lu.ftn.services;

import lu.ftn.model.dto.AssignBetaReadersDTO;
import lu.ftn.model.dto.BetaReaderReviewSubmissionDTO;
import lu.ftn.model.entity.BetaReaderReview;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.Reader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BetaReaderService {

    List<Book> getBooksForReview();

    BetaReaderReview submitReview(BetaReaderReviewSubmissionDTO review, String bookId);

    List<Reader> getPotentialBetaReaders();

    Book assignBetaReaders(AssignBetaReadersDTO dto, String bookId) throws Exception;

    void punishBetaReader(String betaReaderId, Book book) throws Exception;

    void notifyWriterAboutBetaReaderReviews(String email);

    void getBook();

    List<BetaReaderReview> viewReviews(String bookId);
}
