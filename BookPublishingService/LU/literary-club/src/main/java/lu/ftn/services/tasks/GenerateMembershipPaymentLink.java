package lu.ftn.services.tasks;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class GenerateMembershipPaymentLink implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // Mock request
        delegateExecution.setVariable("paymentUrl", "http://localhost:4200/mock_payment");
    }
}
