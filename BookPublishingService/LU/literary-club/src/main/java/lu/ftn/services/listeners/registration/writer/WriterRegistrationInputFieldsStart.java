package lu.ftn.services.listeners.registration.writer;

import lu.ftn.model.entity.Genre;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WriterRegistrationInputFieldsStart implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices().
                getFormService().getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();

        FormField field = formFields.stream().filter(formField -> formField.getId().equals("genres")).collect(Collectors.toList()).get(0);
        field.getProperties().put("choices", Arrays.stream(Genre.values()).map(Enum::toString).collect(Collectors.joining(",")));
    }
}
