package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.BookSuitabilityDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewSubmissionCompleteListener implements TaskListener {

    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;

    public ReviewSubmissionCompleteListener(BookPublishingService bookPublishingService, BookPublishingProcessRepository bookPublishingProcessRepository) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
    }

    @Override
    public void notify(DelegateTask delegateTask) throws BpmnError {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        BookSuitabilityDTO dto = new BookSuitabilityDTO(originalFormSubmissionValues);
        BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        delegateTask.setVariable("suitableForPublishing", dto.getSuitable().equals("YES"));
        bookPublishingService.editorReviewsBookPreview(dto.getSuitable().equals("YES"), bookPublishingProcess.getBook().getId(), bookPublishingProcess.getProcessId());
    }
}
