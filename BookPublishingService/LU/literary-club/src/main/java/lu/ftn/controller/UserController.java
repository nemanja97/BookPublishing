package lu.ftn.controller;

import lu.ftn.model.dto.*;
import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.Role;
import lu.ftn.services.MembershipProcessService;
import lu.ftn.services.StorageService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/users")
@CrossOrigin
public class UserController {

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;

    @Autowired
    MembershipProcessService membershipProcessService;

    @GetMapping(path = "/verify/{processId}", produces = "application/json")
    public ResponseEntity<Object> verifyUserEmail(
        @PathVariable String processId
    ) {
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
            String processInstanceId = processInstance.getId();

            runtimeService.setVariable(processInstanceId, "registrationConfirmed", true);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/writer_registration/membership", produces = "application/json")
    public ResponseEntity<MembershipProcessDTO> getMembershipProcessForLoggedInUser() {
        try {
            MembershipProcess process = membershipProcessService.findByUserId(getCurrentUserId());
            if (process == null)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            MembershipProcessDTO processDTO = new MembershipProcessDTO(process);
            return new ResponseEntity<>(processDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
