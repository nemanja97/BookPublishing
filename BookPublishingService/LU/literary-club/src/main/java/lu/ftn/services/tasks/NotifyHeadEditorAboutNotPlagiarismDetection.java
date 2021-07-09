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
public class NotifyHeadEditorAboutNotPlagiarismDetection implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - NotifyHeadEditorAboutNotPlagiarismDetection");

        User headEditor = userService.findHeadEditor();
        delegateExecution.setVariable("head_editor", headEditor.getId());
        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        PlagiarismProcess plagiarismProcess = plagiarismProcessService.findById(plagiarismProcessId);

        emailNotificationService.sendMessage(
                headEditor.getEmail(),
                "Literary Club - No plagiarism",
                String.format("The book '%s', written by %s, has not been deemed as a plagiarised work.",
                        plagiarismProcess.getBlamedBook().getTitle(), String.format("%s %s", plagiarismProcess.getBlamedWriter().getName(), plagiarismProcess.getBlamedWriter().getSurname())
                )
        );
    }
}
