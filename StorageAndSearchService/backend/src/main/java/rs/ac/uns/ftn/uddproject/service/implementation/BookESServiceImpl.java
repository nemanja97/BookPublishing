package rs.ac.uns.ftn.uddproject.service.implementation;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.BookES;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;
import rs.ac.uns.ftn.uddproject.repository.elasticsearch.BookESRepository;
import rs.ac.uns.ftn.uddproject.service.BookESService;
import rs.ac.uns.ftn.uddproject.service.StorageService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BookESServiceImpl implements BookESService {

  @Autowired private BookESRepository bookESRepository;

  @Autowired private ElasticsearchOperations elasticsearchOperations;

  @Autowired private StorageService storageService;

  private static boolean isPhrase(String text) {
    return text.startsWith("\"") && text.endsWith("\"");
  }

  private static QueryBuilder getBookSearchTitleFieldQuery(String title) {
    return BookESServiceImpl.isPhrase(title)
        ? QueryBuilders.matchPhraseQuery("title", title)
        : QueryBuilders.fuzzyQuery("title", title);
  }

  private static QueryBuilder getBookSearchAuthorFieldQuery(String author) {
    return BookESServiceImpl.isPhrase(author)
        ? QueryBuilders.matchPhraseQuery("writer.fullName", author)
        : QueryBuilders.fuzzyQuery("writer.fullName", author);
  }

  private static QueryBuilder getBookSearchContentFieldQuery(String contentExcerpt) {
    return BookESServiceImpl.isPhrase(contentExcerpt)
        ? QueryBuilders.matchPhraseQuery("content", contentExcerpt)
        : QueryBuilders.matchQuery("content", contentExcerpt);
  }

  private static BoolQueryBuilder getBookSearchGenresFieldQuery(List<Genre> genres) {
    BoolQueryBuilder genresBoolQuery = QueryBuilders.boolQuery();
    for (Genre genre : genres) genresBoolQuery.should(QueryBuilders.matchQuery("genres", genre.toString()));
    return genresBoolQuery;
  }

  @Override
  public SearchPage<BookES> findBooks(
      String query,
      String title,
      String author,
      String contentExcerpt,
      List<Genre> genres,
      Pageable pageable) {
    SearchPage<BookES> page;
    if (query != null && !query.isBlank()) page = getSearchHitsForUserQuery(query, pageable);
    else
      page = getSearchHitsForFieldsQuery(title, author, contentExcerpt, genres, pageable);
    return page;
  }

  @Override
  public String parsePDF(String filePath) throws IOException {
    File pdfFile = storageService.returnFile(filePath);

    PDFParser parser = new PDFParser(new RandomAccessFile(pdfFile, "r"));
    parser.parse();

    PDFTextStripper textStripper = new PDFTextStripper();
    String content = textStripper.getText(parser.getPDDocument());

    parser.getPDDocument().close();
    return content;
  }

  @Override
  public BookES save(BookES bookES) {
    return bookESRepository.save(bookES);
  }

  @Override
  public BookES findById(String id) {
    return bookESRepository.findById(id).orElse(null);
  }

  @Override
  public Page<BookES> findByTitleLike(String query, Pageable pageable) {
    return bookESRepository.findByTitleLike(query, pageable);
  }

  @Override
  public Page<BookES> findByContentContains(String query, Pageable pageable) {
    return bookESRepository.findByContentContains(query, pageable);
  }

  @Override
  public Page<BookES> findByGenresContaining(String query, Pageable pageable) {
    return bookESRepository.findByGenresContaining(query, pageable);
  }

  private SearchPage<BookES> getSearchHitsForFieldsQuery(
      String title, String author, String contentExcerpt, List<Genre> genres, Pageable pageable) {
    BoolQueryBuilder finalizedQuery = QueryBuilders.boolQuery();

    if (title != null && !title.isEmpty()) finalizedQuery.must(BookESServiceImpl.getBookSearchTitleFieldQuery(title));

    if (author != null && !author.isEmpty())
      finalizedQuery.must(BookESServiceImpl.getBookSearchAuthorFieldQuery(author));

    if (contentExcerpt != null && !contentExcerpt.isEmpty())
      finalizedQuery.must(BookESServiceImpl.getBookSearchContentFieldQuery(contentExcerpt));

    if (genres != null && !genres.isEmpty())
      finalizedQuery.must(BookESServiceImpl.getBookSearchGenresFieldQuery(genres));

    NativeSearchQuery nativeSearchQuery =
        new NativeSearchQueryBuilder()
            .withFields(
                "title",
                "writer.fullName",
                "openAccess",
                "price",
                "currency",
                "genres",
                "base64image")
            .withQuery(finalizedQuery)
            .withPageable(pageable)
            .withHighlightFields(
                new HighlightBuilder.Field("content").fragmentSize(500).numOfFragments(1))
            .build();

    SearchHits<BookES> bookESSearchHits =
        elasticsearchOperations.search(nativeSearchQuery, BookES.class);
    return SearchHitSupport.searchPageFor(bookESSearchHits, nativeSearchQuery.getPageable());
  }

  private SearchPage<BookES> getSearchHitsForUserQuery(String query, Pageable pageable) {
    NativeSearchQuery nativeSearchQuery_UserQuery =
        new NativeSearchQueryBuilder()
            .withFields(
                "title",
                "writer.fullName",
                "openAccess",
                "price",
                "currency",
                "genres",
                "base64image")
            .withQuery(QueryBuilders.queryStringQuery(query))
            .withPageable(pageable)
            .withHighlightFields(
                new HighlightBuilder.Field("content").fragmentSize(500).numOfFragments(1))
            .build();

    SearchHits<BookES> bookESSearchHits =
        elasticsearchOperations.search(nativeSearchQuery_UserQuery, BookES.class);
    return SearchHitSupport.searchPageFor(
        bookESSearchHits, nativeSearchQuery_UserQuery.getPageable());
  }
}
