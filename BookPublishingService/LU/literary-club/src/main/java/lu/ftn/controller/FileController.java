package lu.ftn.controller;

import lu.ftn.model.entity.Book;
import com.google.common.collect.Lists;
import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.PlagiarismProcess;
import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.User;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookService;
import lu.ftn.services.MembershipProcessService;
import lu.ftn.services.PlagiarismProcessService;
import lu.ftn.services.StorageService;
import lu.ftn.services.UserService;
import org.apache.commons.io.FileUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/files")
@CrossOrigin
public class FileController {

    @Autowired
    StorageService storageService;

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    MembershipProcessService membershipProcessService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Autowired
    UserService userService;

    @Autowired
    BookPublishingProcessRepository bookPublishingProcessRepository;

    @Autowired
    BookService bookService;

    @GetMapping(path = "/writer_association_submissions/{processId}")
    public ResponseEntity<Object> getWriterAssociationSubmittedWorks(
            @PathVariable String processId
    ) {
        try {
            MembershipProcess membershipProcess = membershipProcessService.findById(processId);
            User user = userService.findOne(getCurrentUserId());

            if (membershipProcess == null || user == null || !(user.getRole() == Role.ROLE_EDITOR || user.getRole() == Role.ROLE_HEAD_EDITOR))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            File zippedSubmissions = storageService.returnFilesAsZip(membershipProcess.getWorks());
            byte[] contents = FileUtils.readFileToByteArray(zippedSubmissions);

            HttpHeaders headers = buildHeaders();
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getBookAsPdf/{bookId}")
    public ResponseEntity<Object> getBookAsPdf(
            @PathVariable("bookId") String bookId
    ) {
        try {
            Book book = bookService.findById(bookId);
            ArrayList<String> bookPaths = new ArrayList<>();
            bookPaths.add(book.getFilePath());
            File zippedSubmissions = storageService.returnFilesAsZip(bookPaths);
            byte[] contents = FileUtils.readFileToByteArray(zippedSubmissions);

            HttpHeaders headers = buildHeaders();
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path = "/plagiarism/{processId}")
    public ResponseEntity<Object> getPlagiarismReportBooks(
            @PathVariable String processId
    ) {
        try {
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(processId).list();
            if (taskList.isEmpty())
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            String plagiarismProcessId = (String) runtimeService.getVariable(taskList.get(0).getExecutionId(), "processId");
            PlagiarismProcess plagiarismProcess = plagiarismProcessService.findById(plagiarismProcessId);
            User user = userService.findOne(getCurrentUserId());

            if (plagiarismProcess == null || user == null || !(user.getRole() == Role.ROLE_EDITOR || user.getRole() == Role.ROLE_HEAD_EDITOR))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            File zippedSubmissions = storageService.returnFilesAsZip(plagiarismProcess.getBlamedBook().getFilePath(), plagiarismProcess.getOriginalBook().getFilePath());
            byte[] contents = FileUtils.readFileToByteArray(zippedSubmissions);

            HttpHeaders headers = buildHeaders();
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String filename = "writer_submissions.zip";
        ContentDisposition contentDisposition = ContentDisposition
                .builder("inline")
                .filename(filename)
                .build();
        headers.setContentDisposition(contentDisposition);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
