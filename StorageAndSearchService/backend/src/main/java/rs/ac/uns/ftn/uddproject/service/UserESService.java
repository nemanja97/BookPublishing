package rs.ac.uns.ftn.uddproject.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.UserES;

public interface UserESService {

  SearchPage<UserES> findReviewersForBook(String bookId, Pageable pageable);

  UserES save(UserES userES);

  UserES findById(String id);
}
