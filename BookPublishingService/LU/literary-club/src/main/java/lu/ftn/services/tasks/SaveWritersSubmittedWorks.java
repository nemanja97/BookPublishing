package lu.ftn.services.tasks;

import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.User;
import lu.ftn.model.entity.Writer;
import lu.ftn.services.MembershipProcessService;
import lu.ftn.services.StorageService;
import lu.ftn.services.UserService;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaveWritersSubmittedWorks implements JavaDelegate {

    @Autowired
    StorageService storageService;

    @Autowired
    MembershipProcessService membershipProcessService;

    @Autowired
    UserService userService;

    @Autowired
    Environment environment;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - SaveWritersSubmittedWorks");

        try {
            ArrayList<String> books = (ArrayList<String>) delegateExecution.getVariable("bookList");
            ArrayList<String> submittedWorksLocations = saveSubmittedWorksToFileSystem(delegateExecution, books);

            MembershipProcess membershipProcess = membershipProcessService.findById(delegateExecution.getProcessInstanceId());
            if (membershipProcess == null) {
                membershipProcess = new MembershipProcess(
                        delegateExecution.getProcessInstanceId(),
                        (Writer) userService.findOne((String) delegateExecution.getVariable("writer")),
                        submittedWorksLocations
                );
            } else {
                membershipProcess.getWorks().addAll(submittedWorksLocations);
            }
            membershipProcessService.save(membershipProcess);

            List<User> editors = userService.findAllEditors();
            delegateExecution.setVariable("editors", editors.stream().map(User::getId).collect(Collectors.toList()));
            incrementNumberOfSubmissions(delegateExecution);
        } catch (Exception ex) {
            // TODO Cleanup after effects
            throw new BpmnError("Unable to save submission", ex.getMessage());
        }
    }

    private ArrayList<String> saveSubmittedWorksToFileSystem(DelegateExecution delegateExecution, List<String> tempPathList) throws IOException {
        ArrayList<String> submittedWorksLocations = new ArrayList<>();
        for (String tempPath : tempPathList) {
            Path tempFileLocation = Paths.get(tempPath);
            Path storedFileLocation = storageService.storeFile(tempFileLocation,
                    String.format("%s/registrationProcess/%s", environment.getProperty("fileServerLocation"), delegateExecution.getProcessInstanceId()),
                    tempFileLocation.getFileName().toString());
            submittedWorksLocations.add(storedFileLocation.toAbsolutePath().toString());
        }
        return submittedWorksLocations;
    }

    private void incrementNumberOfSubmissions(DelegateExecution delegateExecution) {
        Integer numberOfSubmissions = (Integer) delegateExecution.getVariable("number_of_submissions");
        if (numberOfSubmissions == null)
            delegateExecution.setVariable("number_of_submissions", 1);
        else
            delegateExecution.setVariable("number_of_submissions", numberOfSubmissions + 1);
    }
}
