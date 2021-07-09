package lu.ftn.services.tasks.publishing;

import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.model.entity.User;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookService;
import lu.ftn.services.LectorService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class SendSubmissionToLector implements JavaDelegate {

    private final BookService bookService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final LectorService lectorService;
    private final UserService userService;

    public SendSubmissionToLector(BookService bookService, BookPublishingProcessRepository bookPublishingProcessRepository, LectorService lectorService, UserService userService) {
        this.bookService = bookService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.lectorService = lectorService;
        this.userService = userService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String processId = delegateExecution.getProcessInstanceId();
        BookPublishingProcess process = bookPublishingProcessRepository.findByProcessId(processId);
        List<User> lectors = userService.findAllLectors();
        User lector = lectors.get((new Random()).nextInt(lectors.size()));
        process.getBook().setLector(lector);
        process.setLector(lector);
        bookService.save(process.getBook());
        bookPublishingProcessRepository.save(process);
    }
}
