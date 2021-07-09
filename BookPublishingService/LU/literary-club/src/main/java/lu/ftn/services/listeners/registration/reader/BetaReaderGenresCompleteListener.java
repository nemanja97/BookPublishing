package lu.ftn.services.listeners.registration.reader;

import lu.ftn.model.dto.BetaReaderRegistrationDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetaReaderGenresCompleteListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        BetaReaderRegistrationDTO betaReaderRegistrationDTO = new BetaReaderRegistrationDTO(originalFormSubmissionValues);

        delegateTask.setVariable("registration_form_beta_reader", betaReaderRegistrationDTO);
    }
}
