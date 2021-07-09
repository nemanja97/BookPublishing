package lu.ftn.services.listeners.plagiarism;

import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.ReplacementEditorSelectionDTO;
import lu.ftn.model.entity.User;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChooseReplacementEditorCompleteTaskListener implements TaskListener {

    @Autowired
    UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        ReplacementEditorSelectionDTO editorSelectionDTO = new ReplacementEditorSelectionDTO(originalFormSubmissionValues);

        List<User> allEditors = userService.findAllEditors();
        if (allEditors.stream().noneMatch(e -> e.getUsername().equals(editorSelectionDTO.getReplacement_editor()))) {
            throw new BpmnError("Unknown editor chosen");
        }

        User replacementEditor = userService.findUserByUsername(editorSelectionDTO.getReplacement_editor());
        delegateTask.setVariable("chosenEditor", replacementEditor.getId());
    }
}
