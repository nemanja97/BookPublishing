package lu.ftn.services.tasks.publishing;

import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.model.entity.BookStatus;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import lu.ftn.services.BookService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutSubmissionDenial implements JavaDelegate {

    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BookPublishingService bookPublishingService;
    private final BookService bookService;

    public NotifyWriterAboutSubmissionDenial(BookPublishingProcessRepository bookPublishingProcessRepository, BookPublishingService bookPublishingService, BookService bookService) {
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.bookPublishingService = bookPublishingService;
        this.bookService = bookService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("NotifyWriterAboutSubmissionDenial");
        BookPublishingProcess process = bookPublishingProcessRepository.findByProcessId(delegateExecution.getProcessInstanceId());
        process.getBook().setBookStatus(BookStatus.REJECTED);
        bookService.save(process.getBook());
        bookPublishingService.notifyWriterAboutSubmissionDenial(process.getWriter().getEmail());
    }
}
