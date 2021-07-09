package lu.ftn.luaccountingservice.controller;

import lu.ftn.luaccountingservice.model.dto.BookDTO;
import lu.ftn.luaccountingservice.model.dto.BookListDTO;
import lu.ftn.luaccountingservice.model.entity.Book;
import lu.ftn.luaccountingservice.model.entity.Membership;
import lu.ftn.luaccountingservice.model.entity.User;
import lu.ftn.luaccountingservice.service.BookService;
import lu.ftn.luaccountingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/books")
@CrossOrigin
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<Book> books = bookService.findAll();
        List<BookDTO> bookDTOList = books.stream().map(BookDTO::new).collect(Collectors.toList());

        User user = userService.findUserById(getCurrentUserId());
        if (user != null)
            for (BookDTO bookDTO : bookDTOList)
                if (user.getOwnedBooks().stream().map(Book::getId).collect(Collectors.toList()).contains(bookDTO.getId()))
                    bookDTO.setOwned(true);

        return new ResponseEntity<>(new BookListDTO(bookDTOList), HttpStatus.OK);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping(value = "hasMembership")
    public ResponseEntity<Boolean> getMembershipForUser() {
        User user = userService.findUserById(getCurrentUserId());
        if(user == null){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        boolean isMember = user.getMemberships().size() != 0 &&
                user.getMemberships().stream().noneMatch(Membership::isExpired);
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }
}
