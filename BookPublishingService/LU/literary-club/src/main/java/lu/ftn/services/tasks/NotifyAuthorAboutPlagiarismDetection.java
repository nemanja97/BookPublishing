package lu.ftn.services.tasks;

import lu.ftn.model.entity.PlagiarismProcess;
import lu.ftn.model.entity.User;
import lu.ftn.model.entity.Writer;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.PlagiarismProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyAuthorAboutPlagiarismDetection implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - NotifyAuthorAboutPlagiarismDetection");

        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        PlagiarismProcess plagiarismProcess = plagiarismProcessService.findById(plagiarismProcessId);
        Writer plagiarismWriter = plagiarismProcess.getBlamedWriter();

        emailNotificationService.sendMessage(
                plagiarismWriter.getEmail(),
                "Literary Club - Plagiarism",
                String.format("Your book '%s', has been deemed as a plagiarised work.<br/>The work it plagiarised is '%s', by %s %s. It was taken down from the store.",
                        plagiarismProcess.getBlamedBook().getTitle(),
                        plagiarismProcess.getOriginalBook().getTitle(),
                        plagiarismProcess.getOriginalWriter().getName(),
                        plagiarismProcess.getOriginalWriter().getSurname())
        );
    }
}
