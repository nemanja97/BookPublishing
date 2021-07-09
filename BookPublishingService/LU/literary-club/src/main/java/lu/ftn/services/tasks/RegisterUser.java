package lu.ftn.services.tasks;

import lu.ftn.helper.Converter;
import lu.ftn.model.dto.BetaReaderRegistrationDTO;
import lu.ftn.model.dto.ReaderRegistrationDTO;
import lu.ftn.model.dto.WriterRegistrationDTO;
import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.Writer;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUser implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - RegisterUser");

        Role registrationUserRole = (Role) delegateExecution.getVariable("registration_type");

        if (registrationUserRole.equals(Role.ROLE_WRITER)) {
            WriterRegistrationDTO registrationDTO = (WriterRegistrationDTO) delegateExecution.getVariable("registration_form");
            Writer user = Converter.writerRegistrationDTOToWriter(registrationDTO);
            user = (Writer) userService.registerUser(user);

            delegateExecution.setVariable("registrationConfirmed", false);
            delegateExecution.setVariable("userId", user.getId());
        } else {
            ReaderRegistrationDTO registrationDTO = (ReaderRegistrationDTO) delegateExecution.getVariable("registration_form");
            BetaReaderRegistrationDTO betaReaderRegistrationDTO = (BetaReaderRegistrationDTO) delegateExecution.getVariable("registration_form_beta_reader");
            Reader user = Converter.readerRegistrationDTOToUser(registrationDTO, betaReaderRegistrationDTO);
            user = (Reader) userService.registerUser(user);

            delegateExecution.setVariable("registrationConfirmed", false);
            delegateExecution.setVariable("userId", user.getId());
        }
    }
}
