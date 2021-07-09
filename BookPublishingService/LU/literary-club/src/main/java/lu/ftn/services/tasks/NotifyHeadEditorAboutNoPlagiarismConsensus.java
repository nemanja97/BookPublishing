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
public class NotifyHeadEditorAboutNoPlagiarismConsensus implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - NotifyHeadEditorAboutNoPlagiarismConsensus");

        User headEditor = userService.findHeadEditor();
        delegateExecution.setVariable("head_editor", headEditor.getId());
        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        PlagiarismProcess plagiarismProcess = plagiarismProcessService.findById(plagiarismProcessId);

        emailNotificationService.sendMessage(
                headEditor.getEmail(),
                "Literary Club - Possible plagiarism",
                String.format("The decision whether or not the book '%s', written by %s, is a plagiarism has not been reached yet.<br/>Another round of voting will begin. Please choose editors to compare the book with '%s', written by %s.",
                        plagiarismProcess.getBlamedBook().getTitle(), String.format("%s %s", plagiarismProcess.getBlamedWriter().getName(), plagiarismProcess.getBlamedWriter().getSurname()),
                        plagiarismProcess.getOriginalBook().getTitle(), String.format("%s %s", plagiarismProcess.getOriginalWriter().getName(), plagiarismProcess.getOriginalWriter().getSurname()))
        );
    }
}
