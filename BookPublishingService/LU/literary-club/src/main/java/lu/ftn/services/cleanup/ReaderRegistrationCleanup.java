package lu.ftn.services.cleanup;

import lu.ftn.model.entity.User;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderRegistrationCleanup implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - ReaderRegistrationCleanup");

        String username = (String) delegateExecution.getVariable("username");
        User user = userService.findUserByUsername(username);
        userService.deleteById(user.getId());
    }
}
