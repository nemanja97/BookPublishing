package lu.ftn.startup;

import com.google.common.collect.Lists;
import lu.ftn.model.entity.*;
import com.google.common.collect.Sets;
import lu.ftn.model.entity.*;
import lu.ftn.services.BookService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class StartupDataAdder implements ApplicationRunner {

    @Autowired
    UserService userService;
    
    @Autowired
    IdentityService identityService;

    @Autowired
    BookService bookService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Book book1 = new Book("", "book1", (Writer) userService.findUserByUsername("w1"), Sets.newHashSet(Genre.BIOGRAPHY), "C:\\UPP\\registrationProcess\\925c3a82-5cd1-11eb-a2b5-7824af3c29aa\\UPP3337921001473412787.pdf", true, BigDecimal.valueOf(0), "EUR", BookStatus.AVAILABLE);
//        Book book2 = new Book("", "book2", (Writer) userService.findUserByUsername("w2"), Sets.newHashSet(Genre.BIOGRAPHY), "C:\\UPP\\registrationProcess\\925c3a82-5cd1-11eb-a2b5-7824af3c29aa\\UPP3337921001473412787.pdf", true, BigDecimal.valueOf(0), "EUR", BookStatus.AVAILABLE);
//        bookService.save(book1);
//        bookService.save(book2);
        addGroupsIfNotExist();
        addUsersIfNotExist();
    }
    
    private void addGroupsIfNotExist() {
        List<Group> groups = identityService.createGroupQuery().groupIdIn(
                "EDITORS", "WRITERS", "READERS"
        ).list();
        
        if (groups.isEmpty()) {
            createAndSaveGroup("EDITORS");
            createAndSaveGroup("WRITERS");
            createAndSaveGroup("READERS");
        }
    }

    private void createAndSaveGroup(String groupName) {
        Group editorGroup = identityService.newGroup(groupName);
        editorGroup.setId(groupName);
        editorGroup.setName(groupName);
        identityService.saveGroup(editorGroup);
    }

    private void addUsersIfNotExist() {
        List<org.camunda.bpm.engine.identity.User> users = identityService.createUserQuery().list();

        if (users.isEmpty() || users.size() == 1) { // Admin is created by default
            createAndSaveHeadEditor("1");
            createAndSaveUser("2");
            createAndSaveWriter("3");
            createAndSaveReader("4");
            createAndSaveLector("5");
//            createAndSaveUser("3");
//            createAndSaveUser("4");
            createAndSaveUser("6");
            createAndSaveUser("7");
        }
    }

    private void createAndSaveHeadEditor(String id) {
        User user = new User();
        user.setId(id);
        user.setActive(true);
        user.setCity(String.format("EDITOR City %s", id));
        user.setCountry(String.format("EDITOR Country %s", id));
        user.setName(String.format("EDITOR First Name %s", id));
        user.setSurname(String.format("EDITOR Last Name %s", id));
        user.setUsername(String.format("EDITOR %s", id));
        user.setPassword("editor");
        user.setEmail(String.format("editor%s@gmail.com", id));
        user.setRole(Role.ROLE_HEAD_EDITOR);

        userService.registerUser(user);
    }


    private void createAndSaveUser(String id) {
        User user = new User();
        user.setId(id);
        user.setActive(true);
        user.setCity(String.format("EDITOR City %s", id));
        user.setCountry(String.format("EDITOR Country %s", id));
        user.setName(String.format("EDITOR First Name %s", id));
        user.setSurname(String.format("EDITOR Last Name %s", id));
        user.setUsername(String.format("EDITOR %s", id));
        user.setPassword("editor");
        user.setEmail(String.format("editor%s@gmail.com", id));
        user.setRole(Role.ROLE_EDITOR);

        userService.registerUser(user);
    }
    private void createAndSaveWriter(String id) {
        Writer user = new Writer();
        user.setId(id);
        user.setActive(true);
        user.setCity(String.format("WRITER City %s", id));
        user.setCountry(String.format("WRITER Country %s", id));
        user.setName(String.format("WRITER First Name %s", id));
        user.setSurname(String.format("WRITER Last Name %s", id));
        user.setUsername(String.format("WRITER %s", id));
        user.setPassword("writer");
        user.setEmail(String.format("nemanja997+writer%s@gmail.com", id));
        user.setRole(Role.ROLE_WRITER);

        userService.registerUser(user);
    }
    private void createAndSaveReader(String id) {
        Reader user = new Reader();
        user.setId(id);
        user.setActive(true);
        user.setCity(String.format("READER City %s", id));
        user.setCountry(String.format("READER Country %s", id));
        user.setName(String.format("READER First Name %s", id));
        user.setSurname(String.format("READER Last Name %s", id));
        user.setUsername(String.format("READER %s", id));
        user.setPassword("reader");
        user.setEmail(String.format("nemanja997+reader%s@gmail.com", id));
        user.setRole(Role.ROLE_READER);
        user.setBetaReader(true);

        userService.registerUser(user);
    }
    private void createAndSaveLector(String id) {
        User user = new User();
        user.setId(id);
        user.setActive(true);
        user.setCity(String.format("LECTOR City %s", id));
        user.setCountry(String.format("LECTOR Country %s", id));
        user.setName(String.format("LECTOR First Name %s", id));
        user.setSurname(String.format("LECTOR Last Name %s", id));
        user.setUsername(String.format("LECTOR %s", id));
        user.setPassword("lector");
        user.setEmail(String.format("nemanja997+lector%s@gmail.com", id));
        user.setRole(Role.ROLE_LECTOR);

        userService.registerUser(user);
    }

}
