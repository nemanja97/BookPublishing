package lu.ftn.services.tasks;

import lu.ftn.model.entity.Writer;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyWriterAboutMembershipPayment implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    EmailNotificationService notificationService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String writerId = (String) delegateExecution.getVariable("writer");
        Writer writer = (Writer) userService.findOne(writerId);
        if (writer == null) {
            throw new BpmnError("Writer not found");
        }

        notificationService.sendMessage(
                writer.getEmail(),
                "Literary Club - Membership payment",
                String.format("You have been accepted into the writer association. To complete your membership request, you will need to pay the membership fees at %s. After three days, the payment link will expire and you will have to retake the application process", delegateExecution.getVariable("paymentUrl"))
        );
    }
}
