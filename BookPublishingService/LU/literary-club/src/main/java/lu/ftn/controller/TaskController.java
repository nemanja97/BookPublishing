package lu.ftn.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lu.ftn.model.dto.FormFieldsDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.FormSubmissionDTOList;
import lu.ftn.model.dto.TaskDTO;
import lu.ftn.services.StorageService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    IdentityService identityService;

    @Autowired
    StorageService storageService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TaskDTO>> getTasksForLoggedInUser(
            @RequestParam(required = false, defaultValue = "") String taskName
    ) {
        List<Task> tasks;
        if (taskName == null || taskName.isEmpty())
            tasks = taskService.createTaskQuery().taskAssignee(getCurrentUserId()).list();
        else
            tasks = taskService.createTaskQuery().taskName(taskName).taskAssignee(getCurrentUserId()).list();

        List<TaskDTO> taskDTOS = tasks.stream().map
                (t -> new TaskDTO(t.getProcessInstanceId(), t.getId(), t.getName(), t.getAssignee(), t.getCreateTime())).collect(Collectors.toList());

        return new ResponseEntity<>(taskDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/form", produces = "application/json")
    public ResponseEntity<?> getForm(
            @RequestParam(name = "processId") String processInstanceId,
            @RequestParam(name = "taskKey") String taskDefinitionKey
    ) {
        if (
                processInstanceId == null || processInstanceId.isEmpty() ||
                taskDefinitionKey == null || taskDefinitionKey.isEmpty()
        ) {
            return new ResponseEntity<>("Empty processId or taskKey fields", HttpStatus.BAD_REQUEST);
        }

        Task task;
        try {
            identityService.setAuthenticatedUserId(getCurrentUserId());
            if (taskIsNotAssigned(processInstanceId)) {
                task = assignNewTask(processInstanceId, taskDefinitionKey);
            } else {
                task = getAssignedTask(processInstanceId, taskDefinitionKey);
            }
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (task == null) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormField> formFields = taskFormData.getFormFields();

        return new ResponseEntity<>(new FormFieldsDTO(task.getId(), processInstanceId, formFields), HttpStatus.OK);
    }

    @PostMapping(path = "/form/{taskId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "application/json")
    public ResponseEntity<?> submitForm(
            @PathVariable String taskId,
            @RequestPart(value = "form", required = false) String formSubmissionValues,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        identityService.setAuthenticatedUserId(getCurrentUserId());
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null)
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        String processInstanceId = task.getProcessInstanceId();
        Map<String, Object> taskForSubmissionData = new HashMap<>();

        if ((formSubmissionValues == null || formSubmissionValues.isEmpty()) && (files == null || files.length == 0)) {
            return new ResponseEntity<>("Empty payload", HttpStatus.BAD_REQUEST);
        }

        // Handle regular form submission
        if (formSubmissionValues !=  null && !formSubmissionValues.isEmpty()) {
            try {
                FormSubmissionDTOList formSubmissionDTOList = convertRequestObjectToForm(formSubmissionValues);
                validateSubmissionInput(formSubmissionDTOList.getDtoList());

                runtimeService.setVariable(processInstanceId, "originalFormSubmissionValues", formSubmissionDTOList.getDtoList());
                List<FormSubmissionDTO> sanitizedSubmissionDTOs = sanitizeSubmissionInput(formSubmissionDTOList.getDtoList());
                taskForSubmissionData = submissionValuesToMap(sanitizedSubmissionDTOs);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>("Request badly formatted", HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        // Handle files
        if (files.length != 0) {
            ArrayList<String> fileLocations = new ArrayList<>();
            for (MultipartFile file : files) {
                try {
                    fileLocations.add(storageService.storeAsTempFile(file));
                } catch (IOException e) {
                    return new ResponseEntity<>("Could not store files", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            runtimeService.setVariable(processInstanceId, "tempFileLocations", fileLocations);

            String tempFileNames = Arrays.stream(files).map(MultipartFile::getName).collect(Collectors.joining(","));
            taskForSubmissionData.put("files", tempFileNames);
        }

        try {
            formService.submitTaskForm(taskId, taskForSubmissionData);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private FormSubmissionDTOList convertRequestObjectToForm(String formSubmissionValues) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(formSubmissionValues, FormSubmissionDTOList.class);
    }

    private void validateSubmissionInput(List<FormSubmissionDTO> formSubmissionValues) throws Exception {
        for (FormSubmissionDTO submission : formSubmissionValues) {
            if (submission.getFieldId() == null)
                throw new Exception("Submission request malformed");
        }
    }

    private List<FormSubmissionDTO> sanitizeSubmissionInput(List<FormSubmissionDTO> formSubmissionValues) {
        for (FormSubmissionDTO submission : formSubmissionValues) {
            if (submission.getFieldValue() instanceof Collection) {
                try {
                    submission.setFieldValue(((Collection<?>) submission.getFieldValue()).stream().map(Object::toString).collect(Collectors.joining(",")));
                } catch (Exception ex) {
                    submission.setFieldValue(null);
                }
            }
        }
        return formSubmissionValues;
    }

    private Map<String, Object> submissionValuesToMap(List<FormSubmissionDTO> submissionValues) {
        Map<String, Object> submissionMap = new HashMap<>();
        for (FormSubmissionDTO submission : submissionValues) {
            submissionMap.put(submission.getFieldId(), submission.getFieldValue());
        }
        return submissionMap;
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private boolean taskIsNotAssigned(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).taskAssignee(getCurrentUserId()).list().isEmpty();
    }

    private Task assignNewTask(String processInstanceId, String definitionKey) {
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).
                taskDefinitionKey(definitionKey).
                taskUnassigned().list().get(0);

        taskService.claim(task.getId(), getCurrentUserId());
        task.setAssignee(getCurrentUserId());
        return task;
    }

    private Task getAssignedTask(String processInstanceId, String definitionKey) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).
                taskDefinitionKey(definitionKey).
                taskAssignee(getCurrentUserId()).list().get(0);
    }
}
