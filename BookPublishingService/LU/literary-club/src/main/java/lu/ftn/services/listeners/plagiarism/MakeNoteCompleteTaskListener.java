package lu.ftn.services.listeners.plagiarism;

import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.PlagiarismNoteDTO;
import lu.ftn.model.entity.User;
import lu.ftn.services.PlagiarismProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MakeNoteCompleteTaskListener implements TaskListener {

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Autowired
    UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        PlagiarismNoteDTO noteDTO = new PlagiarismNoteDTO(originalFormSubmissionValues);
        plagiarismProcessService.leaveNote((String) delegateTask.getVariable("processId"), getCurrentUserId(), noteDTO.getNotes());

        List<User> editors = userService.findAllEditors();
        delegateTask.setVariable("editors", editors.stream().map(User::getId).collect(Collectors.toList()));
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
