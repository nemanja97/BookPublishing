package rs.ac.uns.ftn.uddproject.service.implementation;

import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uddproject.model.entity.Book;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.UserES;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;
import rs.ac.uns.ftn.uddproject.repository.elasticsearch.UserESRepository;
import rs.ac.uns.ftn.uddproject.service.BookService;
import rs.ac.uns.ftn.uddproject.service.UserESService;

import java.util.Collection;

@Service
public class UserESServiceImpl implements UserESService {

  @Autowired UserESRepository userESRepository;

  @Autowired ElasticsearchOperations elasticsearchOperations;

  @Autowired BookService bookService;

  private static GeoDistanceQueryBuilder beWithingDistanceOfPoint(
      GeoPoint point, double distance, DistanceUnit unit) {
    return QueryBuilders.geoDistanceQuery("location").point(point).distance(distance, unit);
  }

  private static BoolQueryBuilder beInterestedInBetaReadingGenres(Collection<Genre> genres) {
    BoolQueryBuilder userMustBeBetaReaderForGenres = QueryBuilders.boolQuery();
    QueryBuilder genresQueryBuilder;
    for (Genre genre : genres) {
      genresQueryBuilder =
          QueryBuilders.matchQuery("genresInterestedInBetaReading", genre.toString());
      userMustBeBetaReaderForGenres.must(genresQueryBuilder);
    }
    return userMustBeBetaReaderForGenres;
  }

  @Override
  public SearchPage<UserES> findReviewersForBook(String bookId, Pageable pageable) {
    Book book = bookService.findById(bookId);
    UserES writer = findById(book.getWriter().getId());

    BoolQueryBuilder finalizedQuery = QueryBuilders.boolQuery();
    finalizedQuery.must(UserESServiceImpl.beInterestedInBetaReadingGenres(book.getGenres()));
    finalizedQuery.mustNot(
        UserESServiceImpl.beWithingDistanceOfPoint(
            writer.getLocation(), 100, DistanceUnit.KILOMETERS));

    NativeSearchQuery nativeSearchQuery =
        new NativeSearchQueryBuilder()
            .withFields("fullName", "email", "location", "genresInterestedInBetaReading")
            .withQuery(finalizedQuery)
            .withPageable(pageable)
            .build();

    SearchHits<UserES> bookESSearchHits =
        elasticsearchOperations.search(nativeSearchQuery, UserES.class);
    return SearchHitSupport.searchPageFor(bookESSearchHits, nativeSearchQuery.getPageable());
  }

  @Override
  public UserES save(UserES userES) {
    return userESRepository.save(userES);
  }

  @Override
  public UserES findById(String id) {
    return userESRepository.findById(id).orElse(null);
  }
}
