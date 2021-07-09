package lu.ftn.services.implementation;

import lu.ftn.model.dto.LectorReviewDTO;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookStatus;
import lu.ftn.model.entity.LectorReview;
import lu.ftn.model.entity.User;
import lu.ftn.repository.LectorReviewRepository;
import lu.ftn.services.BookService;
import lu.ftn.services.LectorService;
import lu.ftn.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LectorServiceImpl implements LectorService {

    private final BookService bookService;
    private final UserService userService;
    private final LectorReviewRepository lectorReviewRepository;

    public LectorServiceImpl(BookService bookService, UserService userService, LectorReviewRepository lectorReviewRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.lectorReviewRepository = lectorReviewRepository;
    }

    @Override
    public void getBook() {

    }

    @Override
    public LectorReview markErrors(LectorReviewDTO dto, String bookId) throws Exception {
        Book book = bookService.findById(bookId);
        User loggedInUser = userService.getLoggedInUser();

        if(!loggedInUser.getId().equals(book.getLector().getId())){
            throw new Exception("Logged in user is not lector of that book");
        }
        if(dto.getNeedsCorrections().equals("YES")){
            book.setBookStatus(BookStatus.LECTOR_ASKS_FOR_CHANGES);
        }else{
            book.setBookStatus(BookStatus.LECTOR_APPROVES);
        }
        bookService.save(book);
        LectorReview review = new LectorReview(null,book,loggedInUser,dto.getLectorComment(), !dto.getNeedsCorrections().equals("YES"));
        return lectorReviewRepository.save(review);

    }

    @Override
    public List<LectorReview> viewLectorNotes(String bookId) throws Exception {
        User user = userService.getLoggedInUser();
        Book book = bookService.findById(bookId);
        if(!user.getId().equals(book.getWriter().getId())){
            throw new Exception("User is not writer of that book");
        }
        return lectorReviewRepository.findLectorReviewsByBook(book);
    }
}
