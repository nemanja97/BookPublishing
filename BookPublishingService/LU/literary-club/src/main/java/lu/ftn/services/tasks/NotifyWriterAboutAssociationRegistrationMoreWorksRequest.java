package lu.ftn.services.tasks;

import lu.ftn.model.entity.User;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutAssociationRegistrationMoreWorksRequest implements JavaDelegate {
    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - NotifyWriterAboutAssociationRegistrationMoreWorksRequest");

        String writerId = (String) delegateExecution.getVariable("writer");
        User user = userService.findOne(writerId);
        emailNotificationService.sendMessage(
                user.getEmail(),
                "Literary Club - Association member request, request more works",
                "Unfortunately, we require more of your works to make a decision on your membership."
        );
    }
}
