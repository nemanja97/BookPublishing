package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.BookPlagiarismDecisionDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.RequestEditorChangesDTO;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestChangesCompleteListener implements TaskListener {

    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BookPublishingService bookPublishingService;

    public RequestChangesCompleteListener(BookPublishingProcessRepository bookPublishingProcessRepository, BookPublishingService bookPublishingService) {
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.bookPublishingService = bookPublishingService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        RequestEditorChangesDTO dto = new RequestEditorChangesDTO(originalFormSubmissionValues);
        BookPublishingProcess process = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        try {
            bookPublishingService.requestChanges(dto,process.getBook().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
