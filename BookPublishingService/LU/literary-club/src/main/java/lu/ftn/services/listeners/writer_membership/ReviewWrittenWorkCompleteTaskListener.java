package lu.ftn.services.listeners.writer_membership;

import lu.ftn.model.dto.EditorVoteDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.services.MembershipProcessService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewWrittenWorkCompleteTaskListener implements TaskListener {

    @Autowired
    MembershipProcessService membershipProcessService;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        EditorVoteDTO editorVoteDTO = new EditorVoteDTO(originalFormSubmissionValues);

        membershipProcessService.recordVote(editorVoteDTO.getEditor_vote(), getCurrentUserId(), delegateTask.getProcessInstanceId());
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
