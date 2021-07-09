package lu.ftn.luaccountingservice.startup;

import lu.ftn.luaccountingservice.model.entity.Book;
import lu.ftn.luaccountingservice.model.entity.User;
import lu.ftn.luaccountingservice.service.BookService;
import lu.ftn.luaccountingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StartupDataAdder implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Override
    public void run(ApplicationArguments args) {
        addBooksIfNotExist();
        addUsersIfNotExist();
    }
    
    private void addBooksIfNotExist() {
        if (bookService.findAll().isEmpty()) {
            Book b1 = new Book("Glen Cook - The Black Company", "https://images-na.ssl-images-amazon.com/images/I/814A95+aeVL.jpg", BigDecimal.valueOf(5.0), "EUR");
            Book b2 = new Book("J.R.R Tolkien - Fellowship of the Ring", "https://images-na.ssl-images-amazon.com/images/I/91jBdaRVqML.jpg", BigDecimal.valueOf(3.0), "EUR");
            Book b3 = new Book("Isaac Asimov - The Last Question", "https://cdna.artstation.com/p/assets/images/images/011/122/460/large/fran-fdez-la-ultima-pregunta.jpg?1527963912", BigDecimal.valueOf(7.0), "EUR");
            Book b4 = new Book("Douglas Adams - The Ultimate Hitchhiker's Guide To The Galaxy", "https://images.penguinrandomhouse.com/cover/9780345453747", BigDecimal.valueOf(15.0), "EUR");
            Book b5 = new Book("William Gibson - Neuromancer", "https://i.redd.it/uh6dkz82wob31.jpg", BigDecimal.valueOf(10.0), "EUR");
            Book b6 = new Book("Daniel Keyes - Flowers For Algernon", "https://pylesofbooks.files.wordpress.com/2019/01/flowersforalgernon.jpg?w=526&h=681", BigDecimal.valueOf(1000000.0), "EUR");

            bookService.save(b1);
            bookService.save(b2);
            bookService.save(b3);
            bookService.save(b4);
            bookService.save(b5);
            bookService.save(b6);
        }
    }

    private void addUsersIfNotExist() {
        if (userService.findAll().isEmpty()) {
            User u1 = new User("u1@m.com", "u1");
            User u2 = new User("u2@m.com", "u2");
            User u3 = new User("u3@m.com", "u3");

            userService.registerUser(u1);
            userService.registerUser(u2);
            userService.registerUser(u3);
        }
    }

}
