package lu.ftn.services.listeners.writer_membership;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmitWrittenWorksCompleteTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<String> originalFormSubmissionValues = (List<String>) delegateTask.getVariable("tempFileLocations");
        delegateTask.setVariable("bookList", originalFormSubmissionValues);
    }
}
