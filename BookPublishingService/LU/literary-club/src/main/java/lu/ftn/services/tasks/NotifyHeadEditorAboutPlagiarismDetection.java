package lu.ftn.services.tasks;

import lu.ftn.model.entity.PlagiarismProcess;
import lu.ftn.model.entity.User;
import lu.ftn.services.EmailNotificationService;
import lu.ftn.services.PlagiarismProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyHeadEditorAboutPlagiarismDetection implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - NotifyHeadEditorAboutPlagiarismDetection");

        User headEditor = userService.findHeadEditor();
        delegateExecution.setVariable("head_editor", headEditor.getId());
        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        PlagiarismProcess plagiarismProcess = plagiarismProcessService.findById(plagiarismProcessId);

        emailNotificationService.sendMessage(
                headEditor.getEmail(),
                "Literary Club - Plagiarism",
                String.format("The book '%s', written by %s, has been deemed as a plagiarised work.<br/>The work it plagiarised is '%s', by %s. It was taken down from the store.",
                        plagiarismProcess.getBlamedBook().getTitle(), String.format("%s %s", plagiarismProcess.getBlamedWriter().getName(), plagiarismProcess.getBlamedWriter().getSurname()),
                        plagiarismProcess.getOriginalBook().getTitle(), String.format("%s %s", plagiarismProcess.getOriginalWriter().getName(), plagiarismProcess.getOriginalWriter().getSurname()))
        );
    }
}
