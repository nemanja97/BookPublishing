package lu.ftn.services.tasks.publishing;

import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BetaReaderService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutBetaReader implements JavaDelegate {

    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BetaReaderService betaReaderService;

    public NotifyWriterAboutBetaReader(BookPublishingProcessRepository bookPublishingProcessRepository, BetaReaderService betaReaderService) {
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.betaReaderService = betaReaderService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("NotifyWriterAboutBetaReader");
        BookPublishingProcess process = bookPublishingProcessRepository.findByProcessId(delegateExecution.getProcessInstanceId());
        betaReaderService.notifyWriterAboutBetaReaderReviews(process.getWriter().getEmail());
    }
}
