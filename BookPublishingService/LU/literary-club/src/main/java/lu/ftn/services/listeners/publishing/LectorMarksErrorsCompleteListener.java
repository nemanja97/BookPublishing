package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.LectorReviewDTO;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import lu.ftn.services.LectorService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectorMarksErrorsCompleteListener implements TaskListener {
    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final LectorService lectorService;
    public LectorMarksErrorsCompleteListener(BookPublishingService bookPublishingService, BookPublishingProcessRepository bookPublishingProcessRepository, LectorService lectorService) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.lectorService = lectorService;
    }
    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        LectorReviewDTO bookDTO = new LectorReviewDTO(originalFormSubmissionValues);
        BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        try {
            lectorService.markErrors(bookDTO, bookPublishingProcess.getBook().getId());
            delegateTask.setVariable("needsCorrections", bookDTO.getNeedsCorrections().equals("YES"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
