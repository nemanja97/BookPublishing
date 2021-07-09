package lu.ftn.services.listeners.publishing;

import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import lu.ftn.services.BookService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UploadManuscriptCompleteListener implements TaskListener {

    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BookService bookService;

    public UploadManuscriptCompleteListener(BookPublishingService bookPublishingService, BookPublishingProcessRepository bookPublishingProcessRepository, BookService bookService) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.bookService = bookService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        ArrayList<String> path = (ArrayList<String>) delegateTask.getVariable("tempFileLocations");
        System.out.println(path);
        BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        bookPublishingProcess.getBook().setFilePath(path.get(0));
        bookPublishingService.uploadManuscript(bookPublishingProcess.getBook().getId());
        bookService.save(bookPublishingProcess.getBook());

    }
}
