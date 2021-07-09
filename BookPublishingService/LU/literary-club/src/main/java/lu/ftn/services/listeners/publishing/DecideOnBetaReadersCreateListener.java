package lu.ftn.services.listeners.publishing;

import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.User;
import lu.ftn.repository.ReaderRepository;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DecideOnBetaReadersCreateListener implements TaskListener {
    private final ReaderRepository readerRepository;

    public DecideOnBetaReadersCreateListener(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        TaskFormData taskFormData = delegateTask.getExecution().getProcessEngineServices().
                getFormService().getTaskFormData(delegateTask.getId());
        List<FormField> formFields = taskFormData.getFormFields();

        List<Reader> readers =  readerRepository.findByBetaReader(true);
        FormField field = formFields.stream().filter(formField -> formField.getId().equals("betareaders")).collect(Collectors.toList()).get(0);
        List<String> betareaders = readers.stream().map(User::getUsername).collect(Collectors.toList());
        field.getProperties().put("choices", betareaders.stream().collect(Collectors.joining(",")));
    }
}
