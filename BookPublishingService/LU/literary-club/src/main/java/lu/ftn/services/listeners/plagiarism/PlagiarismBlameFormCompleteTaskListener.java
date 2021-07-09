package lu.ftn.services.listeners.plagiarism;

import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.PlagiarismBlameDTO;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlagiarismBlameFormCompleteTaskListener  implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        PlagiarismBlameDTO plagiarismBlameDTO = new PlagiarismBlameDTO(originalFormSubmissionValues);

        delegateTask.setVariable("blame_form", plagiarismBlameDTO);
    }
}
