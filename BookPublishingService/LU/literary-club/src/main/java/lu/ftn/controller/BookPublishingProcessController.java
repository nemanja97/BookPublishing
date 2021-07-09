package lu.ftn.controller;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/book_process")
@CrossOrigin
public class BookPublishingProcessController {

    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BookService bookService;

    public BookPublishingProcessController(BookPublishingProcessRepository bookPublishingProcessRepository, BookService bookService) {
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.bookService = bookService;
    }

    @GetMapping(path = "/process_for_book/{bookId}", produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> getProcessForBook(@PathVariable("bookId") String bookId) {
        Book book = bookService.findById(bookId);
        BookPublishingProcess process = bookPublishingProcessRepository.findByBook(book);
        return new ResponseEntity<>(process.getProcessId(), HttpStatus.OK);
    }
}
