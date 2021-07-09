package lu.ftn.services.listeners.registration.reader;

import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.ReaderRegistrationDTO;
import lu.ftn.model.entity.Role;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderRegistrationInputFieldsComplete implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        ReaderRegistrationDTO registrationDTO = new ReaderRegistrationDTO(originalFormSubmissionValues);

        delegateTask.setVariable("registration_type", Role.ROLE_READER);
        delegateTask.setVariable("registration_form", registrationDTO);
    }
}
