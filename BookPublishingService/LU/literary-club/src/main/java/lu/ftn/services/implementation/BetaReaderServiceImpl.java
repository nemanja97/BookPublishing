package lu.ftn.services.implementation;

import lu.ftn.model.dto.AssignBetaReadersDTO;
import lu.ftn.model.dto.BetaReaderReviewSubmissionDTO;
import lu.ftn.model.entity.*;
import lu.ftn.repository.BookReviewRepository;
import lu.ftn.repository.ReaderRepository;
import lu.ftn.services.BetaReaderService;
import lu.ftn.services.BookService;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BetaReaderServiceImpl implements BetaReaderService {

    private final UserService userService;
    private final BookService bookService;
    private final BookReviewRepository bookReviewRepository;
    private final ReaderRepository readerRepository;
    private final EmailNotificationService emailNotificationService;

    public BetaReaderServiceImpl(UserService userService, BookService bookService, BookReviewRepository bookReviewRepository, ReaderRepository readerRepository, EmailNotificationService emailNotificationService) {
        this.userService = userService;
        this.bookService = bookService;
        this.bookReviewRepository = bookReviewRepository;
        this.readerRepository = readerRepository;
        this.emailNotificationService = emailNotificationService;
    }


    @Override
    public List<Book> getBooksForReview() {
        //load user from security
        User user = userService.getLoggedInUser();
        //get books he is reviewing
        if (user instanceof Reader) {
            if (((Reader) user).isBetaReader()) {
                return new ArrayList<>(((Reader) user).getReviewing());
            }
        }
        return new ArrayList();
    }

    @Override
    public BetaReaderReview submitReview(BetaReaderReviewSubmissionDTO review, String bookId) {
        //Get user from security
        User user = userService.getLoggedInUser();
        //get book and content from dto
        Book book = bookService.findById(bookId);
        //check if user is reviewing book
        Set<Reader> readers = book.getBetaReaders();
        boolean exist = readers.stream().anyMatch(reader -> reader.getId().equals(user.getId()));
        //Check if user has submitted review
        boolean alreadySubmitted = book.getReviews().stream().anyMatch(betaReaderReview -> betaReaderReview.getBetaReader().getId().equals(user.getId()));
        if (exist && user instanceof Reader && !(alreadySubmitted)) {
            //save review
            Reader reader = (Reader) user;
            BetaReaderReview betaReaderReview = new BetaReaderReview(null, book, reader, review.getBetaReaderComment());
            betaReaderReview = bookReviewRepository.save(betaReaderReview);
            // Check if it is last review
            book.getReviews().add(betaReaderReview);
            if (book.getReviews().size() == book.getBetaReaders().size()) {
                //If it is last change book status
                book.setBookStatus(BookStatus.FINISHED_BETA_READING);
                //notify writer
            }
            //save book
            bookService.save(book);
            //return review
            return betaReaderReview;
        }
        return null;
    }

    @Override
    public List<Reader> getPotentialBetaReaders() {
        return readerRepository.findByBetaReader(true);
    }

    @Override
    public Book assignBetaReaders(AssignBetaReadersDTO dto, String bookId) throws Exception {
        //Get betaReader usernames as parameters
        //get book
        Book book = bookService.findById(bookId);

        //Check if user sending request is editor for that book
        User user = userService.getLoggedInUser();
        if (!user.getId().equals(book.getEditor().getId())) {
            throw new Exception("Logged in user is not an editor for this book");
        }

        //Find betareaders from repository
        for (String username : dto.getReviewerUsernames()) {
            Reader reader = readerRepository.findByUsername(username);
            //assign them to the book
            if (reader.isBetaReader()) {
                book.getBetaReaders().add(reader);
            }
        }
        //Change book status
        book.setBookStatus(BookStatus.SENT_TO_BETA_READERS);
        return bookService.save(book);
        //Save
        //Notify them - optionaly
    }

    @Override
    public void punishBetaReader(String betaReaderId, Book book) throws Exception {
        //Get beta reader
        Reader reader = readerRepository.getOne(betaReaderId);
        //Check if betaReader is reviewing book
        boolean reviewing = book.getBetaReaders().stream().anyMatch(reader1 -> reader1.getId().equals(reader.getId()));
        if (!reviewing) {
            throw new Exception("This readers is not reviewing that book");
        } else {
            //check if he has already wrote review
            boolean wroteReview = book.getReviews().stream().anyMatch(betaReaderReview -> betaReaderReview.getBetaReader().getId().equals(reader.getId()));
            if (wroteReview) {
                throw new Exception("This reader had already wrote review for that book");
            }
        }
        //Increase strikes
        reader.setStrikes(reader.getStrikes() + 1);
        // If strikes == 3 stops being beta reader and send email
        if (reader.getStrikes() >= 3) {
            reader.setBetaReader(false);
            //Notify him optionaly
        }
        //Add empty review so that author knows that reviewer has not wrote review
        BetaReaderReview betaReaderReview = new BetaReaderReview(null, book, reader, "AUTOMATED MESSAGE: This reader has not wrote a review in given time");
        betaReaderReview = bookReviewRepository.save(betaReaderReview);
        //Increase counter to the book he was reviewing
        book.getReviews().add(betaReaderReview);
        bookService.save(book);
        readerRepository.save(reader);
    }

    @Override
    public void notifyWriterAboutBetaReaderReviews(String email) {
        //if all reviewers have sent their reviews send email to writer
        emailNotificationService.sendMessage(email, "Beta readers have finished reading your book", "Beta readers have finished reading your book");

    }

    @Override
    public void getBook() {
        //download book in pdf
    }

    @Override
    public List<BetaReaderReview> viewReviews(String bookId) {
        //check if logged in user is writer of the book
        User user = userService.getLoggedInUser();
        Book book = bookService.findById(bookId);
        //if yes get book reviews
        if (book.getWriter().getId().equals(user.getId())) {
            return new ArrayList<>(book.getReviews());
        }
        return new ArrayList<>();
    }
}
