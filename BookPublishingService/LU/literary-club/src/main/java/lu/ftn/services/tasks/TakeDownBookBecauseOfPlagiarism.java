package lu.ftn.services.tasks;

import lu.ftn.services.PlagiarismProcessService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TakeDownBookBecauseOfPlagiarism implements JavaDelegate {

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - TakeDownBookBecauseOfPlagiarism");

        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        plagiarismProcessService.takedownBook(plagiarismProcessId);
    }
}
