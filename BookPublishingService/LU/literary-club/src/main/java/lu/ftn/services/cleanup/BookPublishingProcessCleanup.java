package lu.ftn.services.cleanup;

import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookPublishingProcessCleanup implements JavaDelegate {

    @Autowired
    BookPublishingService bookPublishingService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - BookPublishingProcessCleanup");
        bookPublishingService.deleteById(delegateExecution.getProcessInstanceId());
    }
}
