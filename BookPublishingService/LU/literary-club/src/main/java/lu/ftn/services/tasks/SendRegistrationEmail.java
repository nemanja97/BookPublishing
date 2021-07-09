package lu.ftn.services.tasks;

import lu.ftn.model.dto.ReaderRegistrationDTO;
import lu.ftn.model.dto.WriterRegistrationDTO;
import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.User;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendRegistrationEmail implements JavaDelegate {

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("CAMUNDA - SendRegistrationEmail");

        Role registrationUserRole = (Role) delegateExecution.getVariable("registration_type");
        String username;

        if (registrationUserRole.equals(Role.ROLE_WRITER)) {
            WriterRegistrationDTO registrationDTO = (WriterRegistrationDTO) delegateExecution.getVariable("registration_form");
            username = registrationDTO.getUsername();
        } else {
            ReaderRegistrationDTO registrationDTO = (ReaderRegistrationDTO) delegateExecution.getVariable("registration_form");
            username = registrationDTO.getUsername();
        }

        User user = userService.findUserByUsername(username);
        emailNotificationService.sendMessage(
                user.getEmail(),
                "Literary Club - Please confirm your registration!",
                String.format("Verify <a href=\"http://localhost:4200/verifyAccount/%s\">here</a>", delegateExecution.getProcessInstanceId())
        );
    }
}
