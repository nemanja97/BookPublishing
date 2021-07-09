package lu.ftn.services.tasks;

import lu.ftn.model.entity.MembershipDecision;
import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.MembershipProcessVote;
import lu.ftn.model.entity.MembershipVote;
import lu.ftn.services.MembershipProcessService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DecideOnRegistration implements JavaDelegate {

    @Autowired
    MembershipProcessService membershipProcessService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("DecideOnRegistration");

        MembershipProcess process = membershipProcessService.findById(delegateExecution.getProcessInstanceId());
        Set<MembershipProcessVote> votes = process.getVotes();

        if (votes.stream().anyMatch(v -> v.getVote().equals(MembershipVote.REQUEST_MORE_WORKS))) {
            try {
                recordMembershipProcessDecision(delegateExecution, process, MembershipDecision.REQUEST_MORE_WORKS);
                return;
            } catch (Exception ex) {
                throw new BpmnError("Error saving decision on writer association registration", ex);
            }
        }

        if (votes.stream().filter(v -> v.getVote().equals(MembershipVote.DENY)).count() > (votes.size() / 2)) {
            try {
                recordMembershipProcessDecision(delegateExecution, process, MembershipDecision.DENY);
                return;
            } catch (Exception ex) {
                throw new BpmnError("Error saving decision on writer association registration", ex);
            }
        }

        if (votes.stream().allMatch(v -> v.getVote().equals(MembershipVote.ACCEPT))) {
            try {
                recordMembershipProcessDecision(delegateExecution, process, MembershipDecision.ACCEPT);
                return;
            } catch (Exception ex) {
                throw new BpmnError("Error saving decision on writer association registration", ex);
            }
        }

        try {
            recordMembershipProcessDecision(delegateExecution, process, MembershipDecision.REQUEST_MORE_WORKS);
        } catch (Exception ex) {
            throw new BpmnError("Error saving decision on writer association registration", ex);
        }

    }

    private void recordMembershipProcessDecision(DelegateExecution delegateExecution, MembershipProcess process, MembershipDecision decision) {
        membershipProcessService.makeFinalDecision(process, decision);
        delegateExecution.setVariable("decision", decision);
    }
}
