package lu.ftn.services.listeners.writer_membership;

import lu.ftn.model.entity.MembershipVote;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewWrittenWorkStartTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices().
                getFormService().getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();

        FormField field = formFields.stream().filter(formField -> formField.getId().equals("editor_vote")).collect(Collectors.toList()).get(0);
        field.getProperties().put("choices", Arrays.stream(MembershipVote.values()).map(Enum::toString).collect(Collectors.joining(",")));
    }
}