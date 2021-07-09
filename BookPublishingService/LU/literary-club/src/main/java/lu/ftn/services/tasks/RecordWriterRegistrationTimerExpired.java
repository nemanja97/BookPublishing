package lu.ftn.services.tasks;

import lu.ftn.model.entity.MembershipDecision;
import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.Writer;
import lu.ftn.services.MembershipProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class RecordWriterRegistrationTimerExpired implements JavaDelegate {

    @Autowired
    MembershipProcessService membershipProcessService;

    @Autowired
    UserService userService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - RecordWriterRegistrationTimerExpired");

        try {
            MembershipProcess membershipProcess = membershipProcessService.findById(delegateExecution.getProcessInstanceId());
            if (membershipProcess == null) {
                membershipProcess = new MembershipProcess(
                        delegateExecution.getProcessInstanceId(),
                        (Writer) userService.findOne((String) delegateExecution.getVariable("writer")),
                        new HashSet<>(),
                        MembershipDecision.DENY,
                        new ArrayList<>()
                );
            } else {
                membershipProcess.setDecision(MembershipDecision.DENY);
            }
            membershipProcessService.save(membershipProcess);
            delegateExecution.setVariable("decision", MembershipDecision.DENY);
        } catch (Exception ex) {
            throw new BpmnError("Error while processing that submission timer was exceeded", ex);
        }

    }
}
