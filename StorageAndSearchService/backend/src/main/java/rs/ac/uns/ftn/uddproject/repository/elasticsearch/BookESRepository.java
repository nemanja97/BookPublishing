package rs.ac.uns.ftn.uddproject.repository.elasticsearch;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.BookES;

@Repository
public interface BookESRepository extends ElasticsearchRepository<BookES, String> {

  Page<BookES> findByTitleLike(String query, Pageable pageable);

  Page<BookES> findByContentContains(String query, Pageable pageable);

  Page<BookES> findByGenresContaining(String query, Pageable pageable);
}
