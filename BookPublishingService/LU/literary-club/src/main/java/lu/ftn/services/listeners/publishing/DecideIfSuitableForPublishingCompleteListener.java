package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.DecideSuitableForPublishingDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecideIfSuitableForPublishingCompleteListener implements TaskListener {
    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;

    public DecideIfSuitableForPublishingCompleteListener(BookPublishingService bookPublishingService, BookPublishingProcessRepository bookPublishingProcessRepository) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        DecideSuitableForPublishingDTO dto = new DecideSuitableForPublishingDTO(originalFormSubmissionValues);
        BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        bookPublishingService.decideOnPublishing(bookPublishingProcess.getBook().getId(), dto.getSuitable().equals("YES"));
        delegateTask.setVariable("suitableForPublishing", dto.getSuitable().equals("YES"));

    }
}
