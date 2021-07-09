package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.AssignBetaReadersDTO;
import lu.ftn.model.dto.DecideNeedsBetaReadersDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BetaReaderService;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecideIfSubmissionNeedsBetaReadersCompleteListener implements TaskListener {
    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BetaReaderService betaReaderService;

    public DecideIfSubmissionNeedsBetaReadersCompleteListener(BookPublishingService bookPublishingService,
                                                              BookPublishingProcessRepository bookPublishingProcessRepository,
                                                              BetaReaderService betaReaderService) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.betaReaderService = betaReaderService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        DecideNeedsBetaReadersDTO dto = new DecideNeedsBetaReadersDTO(originalFormSubmissionValues);
        BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        Book book = bookPublishingService.decideIfSubmissionNeedsBetaReaders(bookPublishingProcess.getBook().getId(), dto.getNeedsBetaReaders().equals("YES"));
        delegateTask.setVariable("needsBetaReaders", dto.getNeedsBetaReaders().equals("YES"));
    }
}
