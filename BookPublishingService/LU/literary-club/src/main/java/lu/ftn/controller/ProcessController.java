package lu.ftn.controller;

import lu.ftn.services.MembershipProcessService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/process")
@CrossOrigin
public class ProcessController {

    @Autowired
    IdentityService identityService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    MembershipProcessService membershipProcessService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @GetMapping(produces = "text/plain")
    public @ResponseBody ResponseEntity<String> startProcess(@RequestParam(name = "key") String processInstanceKey) {
        if (processInstanceKey == null || processInstanceKey.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        try {
            String userId = getCurrentUserId();
            identityService.setAuthenticatedUserId(userId);

            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceKey);
            return new ResponseEntity<>(processInstance.getId(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
