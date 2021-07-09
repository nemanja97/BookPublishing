package lu.ftn.services.listeners.plagiarism;

import lu.ftn.model.dto.EditorSelectionDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.entity.User;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChooseEditorsToEvaluateCompleteTaskListener implements TaskListener {

    @Autowired
    UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        EditorSelectionDTO editorSelectionDTO = new EditorSelectionDTO(originalFormSubmissionValues);

        List<User> allEditors = userService.findAllEditors();
        if (allEditors.stream().filter(e -> editorSelectionDTO.getChosen_editors().stream().anyMatch(username -> username.equals(e.getUsername()))).count() !=  editorSelectionDTO.getChosen_editors().size()) {
            throw new BpmnError("Unknown editors chosen");
        }

        List<User> chosenEditors = allEditors.stream().
                filter(e -> editorSelectionDTO.getChosen_editors().stream().anyMatch(username -> username.equals(e.getUsername()))).
                collect(Collectors.toList());
        delegateTask.setVariable("chosenEditors", chosenEditors.stream().map(User::getId).collect(Collectors.toList()));
    }
}
