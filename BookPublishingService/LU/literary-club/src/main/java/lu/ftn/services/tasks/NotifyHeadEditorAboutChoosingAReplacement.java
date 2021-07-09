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
public class NotifyHeadEditorAboutChoosingAReplacement implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - NotifyHeadEditorAboutChoosingAReplacement");

        User headEditor = userService.findHeadEditor();

        String chosenEditorId = (String) delegateExecution.getVariable("chosen_editor");
        User chosenEditor = userService.findOne(chosenEditorId);

        String plagiarismProcessId = (String) delegateExecution.getVariable("processId");
        PlagiarismProcess plagiarismProcess = plagiarismProcessService.findById(plagiarismProcessId);

        emailNotificationService.sendMessage(
                headEditor.getEmail(),
                "Literary Club - Possible plagiarism",
                String.format("%s %s (%s), editor that was assigned to resolve a plagiarism report about the book '%s', written by %s, has not made his report in time.<br/>Please choose their replacement.",
                        chosenEditor.getName(), chosenEditor.getSurname(), chosenEditor.getUsername(),
                        plagiarismProcess.getBlamedBook().getTitle(), String.format("%s %s", plagiarismProcess.getBlamedWriter().getName(), plagiarismProcess.getBlamedWriter().getSurname()))
        );
    }
}
