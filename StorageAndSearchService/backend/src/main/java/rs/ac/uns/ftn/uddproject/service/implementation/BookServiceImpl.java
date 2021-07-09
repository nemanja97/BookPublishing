package rs.ac.uns.ftn.uddproject.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import rs.ac.uns.ftn.uddproject.exception.PlagiarismException;
import rs.ac.uns.ftn.uddproject.model.dto.plagiarism.LoginInfoDTO;
import rs.ac.uns.ftn.uddproject.model.dto.plagiarism.PaperResultPlagiatorDTO;
import rs.ac.uns.ftn.uddproject.model.entity.Book;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.BookES;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;
import rs.ac.uns.ftn.uddproject.repository.jpa.BookRepository;
import rs.ac.uns.ftn.uddproject.service.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

  @Autowired BookRepository bookRepository;

  @Autowired BookESService bookESService;

  @Autowired UserESService userESService;

  @Autowired StorageService storageService;

  @Autowired UserService userService;

  @Autowired
  @Qualifier("plagiarismRestTemplate")
  private RestTemplate plagiarismRestTemplate;

  @Value("${plagiarism.server}")
  private String plagiarismServer;

  @Value("${plagiarism.email}")
  private String plagiarismEmail;

  @Value("${plagiarism.password}")
  private String plagiarismPassword;

  @Value("${plagiarism.similarity}")
  private Double plagiarismSimilarityFactor;

  private static HttpEntity<MultiValueMap<String, Object>> preparePostRequest(
      Book book, MultipartFile file, String userToken) throws IOException {
    HttpHeaders headers = BookServiceImpl.prepareHeaders(userToken);
    MultiValueMap<String, Object> formData = BookServiceImpl.prepareFormData(book, file);

    return new HttpEntity<>(formData, headers);
  }

  private static HttpHeaders prepareHeaders(String userToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.setBearerAuth(userToken);
    return headers;
  }

  private static MultiValueMap<String, Object> prepareFormData(Book book, MultipartFile file)
      throws IOException {
    MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
    formData.add("title", book.getTitle());

    ByteArrayResource byteArrayResource =
        new ByteArrayResource(file.getBytes()) {
          @Override
          public String getFilename() {
            return file.getOriginalFilename();
          }
        };
    formData.add("file", byteArrayResource);
    return formData;
  }

  @Bean("plagiarismRestTemplate")
  public RestTemplate plagiarismRestTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(String id) {
    return bookRepository.findById(id).orElse(null);
  }

  @Override
  public Book save(Book book) {
    return bookRepository.save(book);
  }

  @Override
  @Transactional(rollbackOn = Exception.class)
  public Book save(Book book, MultipartFile file) throws IOException, PlagiarismException {
    try {
      if (!isFileAPlagiarism(book, file)) {
        book = save(book);
        Path bookPath = storageService.saveFile(book.getId(), file);

        storageService.checkIfFileIsPDF(bookPath);
        book.setFilePath(bookPath.toString());
        book = bookRepository.saveAndFlush(book);

        BookES bookES =
            new BookES(
                book.getId(),
                book.getTitle(),
                userESService.findById(book.getWriter().getId()),
                book.getGenres().stream().map(Genre::toString).collect(Collectors.toList()),
                book.getFilePath(),
                book.getCoverImageInBase64(),
                bookESService.parsePDF(book.getFilePath()),
                book.isOpenAccess(),
                book.getPrice(),
                book.getCurrency());
        bookESService.save(bookES);

        return book;
      } else throw new PlagiarismException();
    } catch (Exception ex) {
      // TODO File cleanup
      throw ex;
    }
  }

  private boolean isFileAPlagiarism(Book book, MultipartFile file) throws IOException {
    String userToken = getUserToken();
    HttpEntity<MultiValueMap<String, Object>> requestEntity =
        BookServiceImpl.preparePostRequest(book, file, userToken);
    ResponseEntity<PaperResultPlagiatorDTO> plagiarismResult = getPlagiarismResult(requestEntity);

    return Objects.requireNonNull(plagiarismResult.getBody()).getSimilarPapers().stream()
        .anyMatch(p -> p.getSimilarProcent() >= plagiarismSimilarityFactor);
  }

  private ResponseEntity<PaperResultPlagiatorDTO> getPlagiarismResult(
      HttpEntity<MultiValueMap<String, Object>> requestEntity) {
    return plagiarismRestTemplate.postForEntity(
        String.format("%s/api/file/upload/new", plagiarismServer),
        requestEntity,
        PaperResultPlagiatorDTO.class);
  }

  private String getUserToken() {
    ResponseEntity<String> userTokenResponse =
        plagiarismRestTemplate.postForEntity(
            String.format("%s/api/login", plagiarismServer),
            new LoginInfoDTO(plagiarismEmail, plagiarismPassword),
            String.class);
    String userToken = userTokenResponse.getBody();
    userToken = Objects.requireNonNull(userToken).substring(1, userToken.length() - 1);
    return userToken;
  }
}
