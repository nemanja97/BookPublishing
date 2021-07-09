package lu.ftn.services.tasks.publishing;

import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotifyEditorViaEmail implements JavaDelegate {
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BookPublishingService bookPublishingService;
    public NotifyEditorViaEmail(BookPublishingProcessRepository bookPublishingProcessRepository, BookPublishingService bookPublishingService) {
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.bookPublishingService = bookPublishingService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("NotifyEditorViaEmail");
        BookPublishingProcess process = bookPublishingProcessRepository.findByProcessId(delegateExecution.getProcessInstanceId());
        bookPublishingService.notifyEditor(process.getEditor().getEmail());
    }
}
