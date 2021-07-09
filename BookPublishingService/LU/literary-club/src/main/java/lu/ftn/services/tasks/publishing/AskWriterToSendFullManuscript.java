package lu.ftn.services.tasks.publishing;

import lu.ftn.model.entity.User;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AskWriterToSendFullManuscript implements JavaDelegate {

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("AskWriterToSendFullManuscript");

        String writerId = (String) delegateExecution.getVariable("writer");
        User user = userService.findOne(writerId);
        emailNotificationService.sendMessage(
                user.getEmail(),
                "Literary Club - Book preview accepted",
                "Please submit full manuscript!"
        );
    }
}
