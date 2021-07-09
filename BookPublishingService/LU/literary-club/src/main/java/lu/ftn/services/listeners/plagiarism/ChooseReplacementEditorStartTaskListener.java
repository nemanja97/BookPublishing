package lu.ftn.services.listeners.plagiarism;

import lu.ftn.model.entity.User;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChooseReplacementEditorStartTaskListener implements TaskListener {

    @Autowired
    UserService userService;

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices().
                getFormService().getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();

        List<User> possibleEditors = userService.findAllEditors();
        FormField field = formFields.stream().filter(formField -> formField.getId().equals("replacement_editor")).collect(Collectors.toList()).get(0);
        field.getProperties().put("choices", possibleEditors.stream().map(user -> String.format("%s %s (%s)", user.getName(), user.getSurname(), user.getUsername())).collect(Collectors.joining(",")));
    }
}
