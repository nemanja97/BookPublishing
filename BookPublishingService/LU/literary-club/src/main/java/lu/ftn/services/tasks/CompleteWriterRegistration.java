package lu.ftn.services.tasks;

import lu.ftn.model.entity.Writer;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompleteWriterRegistration implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String writerId = (String) delegateExecution.getVariable("writer");
        Writer writer = (Writer) userService.findOne(writerId);
        if (writer == null) {
            throw new BpmnError("Writer not found");
        }

        writer.setAccepted(true);
        userService.save(writer);
    }
}
