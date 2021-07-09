package lu.ftn.services.tasks;

import lu.ftn.model.entity.PlagiarismDecision;
import lu.ftn.model.entity.PlagiarismProcess;
import lu.ftn.services.PlagiarismProcessService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecideOnPlagiarism implements JavaDelegate {

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - DecideOnPlagiarism");

        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        PlagiarismProcess process = plagiarismProcessService.decideOnOutcome(plagiarismProcessId);

        switch (process.getDecision()) {
            case PLAGIARISM: delegateExecution.setVariable("plagiarism", true); break;
            case NOT_PLAGIARISM: delegateExecution.setVariable("plagiarism", false); break;
            default: delegateExecution.setVariable("plagiarism", null); break;
        }
    }
}
