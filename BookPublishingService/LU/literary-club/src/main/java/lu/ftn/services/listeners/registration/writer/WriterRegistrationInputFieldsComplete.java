package lu.ftn.services.listeners.registration.writer;

import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.WriterRegistrationDTO;
import lu.ftn.model.entity.Role;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WriterRegistrationInputFieldsComplete implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        WriterRegistrationDTO registrationDTO = new WriterRegistrationDTO(originalFormSubmissionValues);

        delegateTask.setVariable("registration_type", Role.ROLE_WRITER);
        delegateTask.setVariable("registration_form", registrationDTO);
    }
}
