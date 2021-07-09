package lu.ftn.services.cleanup;

import lu.ftn.services.MembershipProcessService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WriterAssociationProcessCleanup implements JavaDelegate {

    @Autowired
    MembershipProcessService membershipProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - WriterAssociationProcessCleanup");
        membershipProcessService.deleteById(delegateExecution.getProcessInstanceId());
    }
}
