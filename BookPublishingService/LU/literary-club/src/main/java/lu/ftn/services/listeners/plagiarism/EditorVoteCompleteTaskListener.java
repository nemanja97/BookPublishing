package lu.ftn.services.listeners.plagiarism;

import lu.ftn.model.dto.EditorPlagiarismVoteDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.services.PlagiarismProcessService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditorVoteCompleteTaskListener implements TaskListener {

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        EditorPlagiarismVoteDTO voteDTO = new EditorPlagiarismVoteDTO(originalFormSubmissionValues);

        plagiarismProcessService.castVote((String) delegateTask.getVariable("processId"), getCurrentUserId(), voteDTO.getPlagiarism_vote());
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
