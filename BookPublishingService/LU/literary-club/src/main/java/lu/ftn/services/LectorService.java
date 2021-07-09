package lu.ftn.services;

import lu.ftn.model.dto.LectorReviewDTO;
import lu.ftn.model.entity.LectorReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LectorService {
    void getBook();
    LectorReview markErrors(LectorReviewDTO dto, String bookId) throws Exception ;
    List<LectorReview> viewLectorNotes(String bookId) throws Exception ;
}
