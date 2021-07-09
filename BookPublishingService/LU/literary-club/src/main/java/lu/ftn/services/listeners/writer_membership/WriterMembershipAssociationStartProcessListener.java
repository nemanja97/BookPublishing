package lu.ftn.services.listeners.writer_membership;

import lu.ftn.model.entity.MembershipDecision;
import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.User;
import lu.ftn.services.MembershipProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WriterMembershipAssociationStartProcessListener implements ExecutionListener {

    @Autowired
    MembershipProcessService membershipProcessService;

    @Autowired
    UserService userService;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String userId = getCurrentUserId();
        User user = userService.findOne(userId);
        if (user == null || !user.getRole().equals(Role.ROLE_WRITER))
            throw new Exception();

        MembershipProcess process = membershipProcessService.findByUserId(userId);
        if (process != null && process.getDecision() != MembershipDecision.DENY)
            throw new Exception();

        delegateExecution.setVariable("writer", userId);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
