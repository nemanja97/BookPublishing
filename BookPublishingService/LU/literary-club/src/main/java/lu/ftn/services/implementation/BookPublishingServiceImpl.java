package lu.ftn.services.implementation;

import lu.ftn.model.dto.*;
import lu.ftn.model.entity.*;
import lu.ftn.repository.*;
import lu.ftn.services.BookPublishingService;
import lu.ftn.services.BookService;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookPublishingServiceImpl implements BookPublishingService {

    private final BookService bookService;
    private final UserService userService;
    private final EditorChangesRepository editorChangesRepository;
    private final EmailNotificationService emailNotificationService;

    private final WriterRepository writerRepository;
    private final EditorBookTaskRepository editorBookTaskRepository;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final ReaderRepository readerRepository;

    public BookPublishingServiceImpl(BookService bookService, UserService userService, EditorChangesRepository editorChangesRepository, EmailNotificationService emailNotificationService, WriterRepository writerRepository, EditorBookTaskRepository editorBookTaskRepository, BookPublishingProcessRepository bookPublishingProcessRepository, ReaderRepository readerRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.editorChangesRepository = editorChangesRepository;
        this.emailNotificationService = emailNotificationService;
        this.writerRepository = writerRepository;
        this.editorBookTaskRepository = editorBookTaskRepository;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.readerRepository = readerRepository;
    }

    @Override
    public Book createBook(BookPublishingDTO dto) {
        User user = userService.getLoggedInUser();
        Writer writer = (Writer) userService.findOne(user.getId());
        if (user.getRole().equals(Role.ROLE_WRITER)) {
            writer = writerRepository.findById(user.getId()).orElse(null);
        }
        Book book = new Book(dto.getTitle(), new HashSet<>(dto.getGenres()), dto.getSynopsis(), writer);
        book.setBookStatus(BookStatus.CREATED);
        book = bookService.save(book);
        return book;
    }

    @Override
    public Book assignEditor(Book book) {
        List<User> editors = userService.findAllEditors();
        int random = (new Random()).nextInt(editors.size());
        User editor = editors.get(random);
        book.setEditor(editor);
        return bookService.save(book);
    }

    @Override
    public void notifyEditor(String email) {
        emailNotificationService.sendMessage(email, "There is new book submitted", "There is new book preview waiting for you to review");
    }

    @Override
    public Book editorReviewsBookPreview(boolean suitable, String bookId, String process) throws BpmnError {

        if (process == null)
            throw new BpmnError("No membership process exists under that ID");
        //check if logged in user is editor
        User user = userService.getLoggedInUser();
        Book book = bookService.findById(bookId);
        if (!book.getEditor().getId().equals(user.getId())) {
            throw new BpmnError("No editor exists under that ID");
        }
        //if suitable change status
        //if not send email about rejection and change status
        if (suitable) {
            book.setBookStatus(BookStatus.WAITING_FULL_MANUSCRIPT);
        } else {
            book.setBookStatus(BookStatus.REJECTED);
            notifyWriterAboutSubmissionDenial(book.getWriter().getEmail());
        }
        return bookService.save(book);
    }

    @Override
    public void notifyWriterAboutSubmissionDenial(String email) {
        emailNotificationService.sendMessage(email, "Your book submission has been denied", "Your book submission has been denied");
    }


    @Override
    public Book uploadManuscript(String bookId) {
        Book book = bookService.findById(bookId);
        if (book.getBookStatus() == BookStatus.WAITING_FULL_MANUSCRIPT) {
            book.setBookStatus(BookStatus.UPLOADED_FULL_MANUSCRIPT);
        } else if (book.getBookStatus() == BookStatus.FINISHED_BETA_READING || book.getBookStatus() == BookStatus.LECTOR_ASKS_FOR_CHANGES) {
            book.setBookStatus(BookStatus.RE_UPLOADED_MANUSCRIPT);
        } else if (book.getBookStatus() == BookStatus.WAITING_FINAL_CHANGES) {
            book.setBookStatus(BookStatus.LECTOR_APPROVES);
        }
        return bookService.save(book);
    }

    @Override
    public Book decideOnPublishing(String bookId, boolean publish) {
        Book book = bookService.findById(bookId);
        if (book.getBookStatus() == BookStatus.LECTOR_APPROVES){
            book.setBookStatus(BookStatus.EDITOR_ASKS_FOR_CHANGES);
        }else{
            if (publish) {
                book.setBookStatus(BookStatus.DECIDE_ON_BETAREADERS);
            } else {
                book.setBookStatus(BookStatus.REJECTED);
            }
        }

        return bookService.save(book);
    }

    @Override
    public Book decideIfSubmissionNeedsBetaReaders(String bookId, boolean needsBetaReaders) {
        Book book = getBook(bookId);
        if (needsBetaReaders) {
            book.setBookStatus(BookStatus.DECIDED_ON_BETAREADERS);
        } else {
            book.setBookStatus(BookStatus.RE_UPLOADED_MANUSCRIPT);
        }
        return book;
    }

    @Override
    public Book decideIfSubmissionNeedsChanges(String bookId, boolean needsChanges) {
        Book book = bookService.findById(bookId);
        if (needsChanges) {
            book.setBookStatus(BookStatus.FINISHED_BETA_READING);
        } else {
            book.setBookStatus(BookStatus.SENT_TO_LECTOR);
        }

        return book;
    }

   /* @Override
    public Book sendSubmissionToLector(SendToLectorDTO dto) throws Exception {
        //get book
        Book book = bookService.findById(dto.getBookId());
        //check if logged in user is editor of book
        User loggedInUser = userService.getLoggedInUser();
        if (!book.getWriter().getId().equals(loggedInUser.getId())) {
            throw new Exception("Logged in user is not editor of this book");
        }
        //assign lector to book
        User lector = userService.findUserByUsername(dto.getLectorUsername());
        //change book status
        book.setBookStatus(BookStatus.SENT_TO_LECTOR);
        //save book
        return bookService.save(book);
    }*/

    @Override
    public void notifyWriterAboutLectorNotes(String email) {
        emailNotificationService.sendMessage(email, "Lector has reviewed your book", "Visit application to view review");
    }


    @Override
    public EditorChanges requestChanges(EditorChangesDTO dto) throws Exception {
        //Get logged in user
        Book book = bookService.findById(dto.getBookId());
        User user = userService.getLoggedInUser();
        // Check if user is editor of that book
        if (!book.getEditor().getId().equals(user.getId())) {
            throw new Exception("User is not editor of that book");
        }
        //Change book status
        book.setBookStatus(BookStatus.EDITOR_ASKS_FOR_CHANGES);
        bookService.save(book);
        //Send changes to book
        EditorChanges changes = new EditorChanges(null, book, dto.getComment());
        return editorChangesRepository.save(changes);
    }

    @Override
    public Book publishBook(String bookId) throws Exception {
        //check if logged in user is editor
        User loggedInUser = userService.getLoggedInUser();
        Book book = bookService.findById(bookId);
        if (!book.getEditor().getId().equals(loggedInUser.getId())) {
            throw new Exception("Use is not editor of this book");
        }
        //change book status
        book.setBookStatus(BookStatus.PUBLISHED);
        //save book
        return bookService.save(book);
    }

    @Override
    public List<Book> getEditorBooks() {
        User loggedInUser = userService.getLoggedInUser();
        List<Book> books = bookService.findAllByEditor(loggedInUser);
        return books;
    }

    @Override
    public List<Book> getLectorBooks() {
        User loggedInUser = userService.getLoggedInUser();
        List<Book> books = bookService.findAllByLector(loggedInUser);
        return books;
    }

    @Override
    public List<Book> getReaderBooks() {
        User loggedInUser = userService.getLoggedInUser();
        Reader reader = readerRepository.findById(loggedInUser.getId()).get();
        //List<Book> books = bookService.findAllByBetaReadersContaining(reader);
        List<Book> books = new ArrayList<>(reader.getReviewing());
        return books;
    }

    @Override
    public void deleteById(String id) {
        bookPublishingProcessRepository.deleteById(id);
    }

    @Override
    public Book getBook(String id) {
        return bookService.findById(id);
    }

    @Override
    public Book decideOnPlagiarism(String bookId, boolean value) {
        Book book = bookService.findById(bookId);
        if (value) {
            book.setBookStatus(BookStatus.REJECTED);
        } else {
            book.setBookStatus(BookStatus.NOT_PLAGIARISM);
        }
        return bookService.save(book);
    }

    @Override
    public Book decideSuitableForPublishing(String bookId, boolean suitable) {
        Book book = bookService.findById(bookId);
        if (suitable) {
            book.setBookStatus(BookStatus.PUBLISHED);
        } else {
            book.setBookStatus(BookStatus.EDITOR_ASKS_FOR_CHANGES);
        }
        return bookService.save(book);
    }

    @Override
    public EditorChanges requestChanges(RequestEditorChangesDTO dto, String bookId) throws Exception {
        //Get logged in user
        Book book = bookService.findById(bookId);
        User user = userService.getLoggedInUser();
        // Check if user is editor of that book
        if (!book.getEditor().getId().equals(user.getId())) {
            throw new Exception("User is not editor of that book");
        }
        //Change book status
        book.setBookStatus(BookStatus.WAITING_FINAL_CHANGES);
        bookService.save(book);
        //Send changes to book
        EditorChanges changes = new EditorChanges(null, book, dto.getChanges());
        return editorChangesRepository.save(changes);
    }

    public List<EditorChanges> getEditorChanges(String bookId) {
        Book book = bookService.findById(bookId);
        return editorChangesRepository.findAllByBook(book);
    }

    public List<Book> getWriterBooks() {
        User user = userService.getLoggedInUser();
        Writer writer = writerRepository.findById(user.getId()).get();
        List<Book> books = bookService.findAllByWriter(writer);
        return books;
    }

}
