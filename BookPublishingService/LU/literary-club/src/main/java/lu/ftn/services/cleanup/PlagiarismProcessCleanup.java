package lu.ftn.services.cleanup;

import lu.ftn.model.entity.User;
import lu.ftn.services.PlagiarismProcessService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlagiarismProcessCleanup implements JavaDelegate {

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - PlagiarismProcessCleanup");

        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        plagiarismProcessService.deleteById(plagiarismProcessId);
    }
}
