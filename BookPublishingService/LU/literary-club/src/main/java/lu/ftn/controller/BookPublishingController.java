package lu.ftn.controller;

import lu.ftn.model.dto.*;
import lu.ftn.model.entity.*;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.repository.EditorBookTaskRepository;
import lu.ftn.services.*;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.camunda.bpm.engine.task.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/publishing")
@CrossOrigin
public class BookPublishingController {

    private final BookPublishingService bookPublishingService;
    private final LectorService lectorService;
    private final BetaReaderService betaReaderService;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final FormService formService;
    private final EditorBookTaskRepository editorBookTaskRepository;
    private final StorageService storageService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final UserService userService;
    private final BookService bookService;

    public BookPublishingController(BookPublishingService bookPublishingService, LectorService lectorService, BetaReaderService betaReaderService, TaskService taskService, RuntimeService runtimeService, FormService formService, EditorBookTaskRepository editorBookTaskRepository, StorageService storageService, BookPublishingProcessRepository bookPublishingProcessRepository, UserService userService, BookService bookService) {
        this.bookPublishingService = bookPublishingService;
        this.lectorService = lectorService;
        this.betaReaderService = betaReaderService;
        this.taskService = taskService;
        this.runtimeService = runtimeService;
        this.formService = formService;
        this.editorBookTaskRepository = editorBookTaskRepository;
        this.storageService = storageService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    /*@CrossOrigin
    @PostMapping(value = "sendPublishingRequest/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> sendBookPublishingRequest(@RequestBody BookPublishingDTO dto, @PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        runtimeService.setVariable(processInstanceId, "book_form", dto);
        try {
            Book book = bookPublishingService.createBook(dto);
            BookPublishingProcess bookPublishingProcess = new BookPublishingProcess(processInstanceId, book, book.getEditor(), book.getWriter());
            bookPublishingProcessRepository.save(bookPublishingProcess);

            formService.submitTaskForm(taskId, dto.toMap());
            runtimeService.setVariable(processInstanceId, "writer", book.getWriter().getId());

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (FormFieldValidatorException | NullPointerException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping(value = "editorBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookPreviewDTO>> getEditorsBooks() {
        List<Book> books = bookPublishingService.getEditorBooks();
        List<BookPreviewDTO> dto = books.stream().map(BookPreviewDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "writerBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookPreviewDTO>> getWriterBooks() {
        List<Book> books = bookPublishingService.getWriterBooks();
        List<BookPreviewDTO> dto = books.stream().map(BookPreviewDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "bookDetails/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getBook(@PathVariable("bookId") String bookId) {
        Book book = bookPublishingService.getBook(bookId);
        BookDTO dto = new BookDTO(book);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /*@PostMapping(value = "editorSubmitsInitialReview/{bookId}/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> editorSubmitsInitialReview(@RequestBody BookSuitabilityDTO dto, @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            Book book = bookPublishingService.editorReviewsBookPreview(dto, bookId, processInstanceId);
            runtimeService.setVariable(processInstanceId, "suitableForPublishing", dto.isSuitable());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(book, taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping(value = "upload_manuscript/{taskId}/{bookId}")
    public ResponseEntity<Object> uploadManuscript(@RequestPart("manuscript") MultipartFile[] manuscriptList, @PathVariable("taskId") String taskId, @PathVariable("bookId") String bookId) {
        MultipartFile manuscript = null;
        for (MultipartFile book : manuscriptList) {
            manuscript = book;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> booksList = new ArrayList<>();
        try {
            String path = storageService.storeAsTempFile(manuscript);
            BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(processInstanceId);
            bookPublishingProcess.getBook().setFilePath(path);
            bookService.save(bookPublishingProcess.getBook());
            booksList.add(path);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not store files", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        runtimeService.setVariable(processInstanceId, "bookList", booksList);

        try {
            String bookName = manuscript.getName();

            HashMap<String, Object> formFieldMap = new HashMap<>();
            formFieldMap.put("manuscript", bookName);

            formService.submitTaskForm(taskId, formFieldMap);
            String editor = (String) runtimeService.getVariable(processInstanceId, "editor");
            Task task2 = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("Decide if submission is plagiarised").singleResult();
            task2.setAssignee(editor);
            taskService.saveTask(task2);
            bookPublishingService.uploadManuscript(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FormFieldValidatorException | NullPointerException | BpmnError ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("decide_if_plagiarism/{bookId}/{taskId}")
    public ResponseEntity<Object> decideIfSubmissionIsPlagiarised(@RequestBody BookPlagiarismDecisionDTO dto,
                                                                  @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            Book book = bookPublishingService.decideOnPlagiarism(bookId, dto.isPlagiarism());
            runtimeService.setVariable(processInstanceId, "plagiarism", dto.isPlagiarism());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(book, taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    //Step after it is decided that it is not plagiarism
    /*@PostMapping("decide_on_publishing/{bookId}/{taskId}")
    public ResponseEntity<Object> decideOnPublishing(@RequestBody DecideOnPublishingDTO dto,
                                                     @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            bookPublishingService.decideOnPublishing(bookId, dto.isPublish());
            runtimeService.setVariable(processInstanceId, "shouldBePublished", dto.isPublish());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("decide_if_needs_betareaders/{bookId}/{taskId}")
    public ResponseEntity<Object> decideIfNeedsBetaReaders(@RequestBody DecideNeedsBetaReadersDTO dto,
                                                           @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            Book book = bookPublishingService.decideIfSubmissionNeedsBetaReaders(bookId, dto.isNeedsBetaReaders());
            runtimeService.setVariable(processInstanceId, "needsBetaReaders", dto.isNeedsBetaReaders());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(book, "", ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("pick_betareaders/{bookId}/{taskId}")
    public ResponseEntity<Object> pickBetaReaders(@RequestBody AssignBetaReadersDTO dto,
                                                  @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            Book book = betaReaderService.assignBetaReaders(dto, bookId);
            List<User> users = new ArrayList<>();
            for (String username : dto.getReviewerUsernames()) {
                users.add(userService.findUserByUsername(username));
            }
            runtimeService.setVariable(processInstanceId, "selectedBetaReaders", users.stream().map(User::getId).collect(Collectors.toList()));
            formService.submitTaskForm(taskId, dto.toMap());
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("Leave review").list();
            int i = 0;
            for (Reader reader : book.getBetaReaders()) {
                tasks.get(i).setAssignee(reader.getId());
                taskService.saveTask(tasks.get(i));
                i++;
            }
            return new ResponseEntity<>(new EditorBookDTO(book, taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("betareader_review/{bookId}/{taskId}")
    public ResponseEntity<Object> betaReaderSubmitsReview(@RequestBody BetaReaderReviewSubmissionDTO dto,
                                                          @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        try {
            betaReaderService.submitReview(dto, bookId);
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping(value = "upload_manuscript_again/{taskId}/{bookId}")
    public ResponseEntity<Object> uploadManuscriptAgain(@RequestPart("manuscript") MultipartFile[] manuscriptList, @PathVariable("taskId") String taskId, @PathVariable("bookId") String bookId) {
        MultipartFile manuscript = null;
        for (MultipartFile book : manuscriptList) {
            manuscript = book;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> booksList = new ArrayList<>();
        try {
            String path = storageService.storeAsTempFile(manuscript);
            BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(processInstanceId);
            bookPublishingProcess.getBook().setFilePath(path);
            bookService.save(bookPublishingProcess.getBook());
            booksList.add(path);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not store files", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        runtimeService.setVariable(processInstanceId, "bookList", booksList);

        try {
            String bookName = manuscript.getName();

            HashMap<String, Object> formFieldMap = new HashMap<>();
            formFieldMap.put("manuscript", bookName);

            formService.submitTaskForm(taskId, formFieldMap);
            String editor = (String) runtimeService.getVariable(processInstanceId, "editor");
            Task task2 = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("Decide if submission needs changes").singleResult();
            task2.setAssignee(editor);
            taskService.saveTask(task2);
            bookPublishingService.uploadManuscript(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FormFieldValidatorException | NullPointerException | BpmnError ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("decide_on_changes/{bookId}/{taskId}")
    //Step before lector
    public ResponseEntity<Object> decideIfSubmissionNeedsChanges(@RequestBody DecideNeedsChangesDTO dto,
                                                                 @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            Book book = bookPublishingService.decideIfSubmissionNeedsChanges(bookId, dto.isNeedsChanges());
            runtimeService.setVariable(processInstanceId, "needsChanges", dto.isNeedsChanges());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(book, taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/


    /*@PostMapping("lector_marks_error/{bookId}/{taskId}")
    public ResponseEntity lectorMarksErrors(@RequestBody LectorReviewDTO dto,
                                            @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            LectorReview lectorReview = lectorService.markErrors(dto, bookId);
            runtimeService.setVariable(processInstanceId, "needsCorrections", dto.isNeedsCorrections());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(lectorReview.getBook(), taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("decide_if_suitable_for_publishing/{bookId}/{taskId}")
    public ResponseEntity<Object> decideIfSuitableForPublishing(@RequestBody DecideSuitableForPublishingDTO dto,
                                                                @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            Book book = bookPublishingService.decideOnPublishing(bookId, dto.isSuitable());
            runtimeService.setVariable(processInstanceId, "suitableForPublishing", dto.isSuitable());
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(book, taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    /*@PostMapping("request_editor_changes/{bookId}/{taskId}")
    public ResponseEntity<Object> requestEditorChanges(@RequestBody RequestEditorChangesDTO dto,
                                                       @PathVariable("bookId") String bookId, @PathVariable("taskId") String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        try {
            runtimeService.setVariable(processInstanceId, "changes", dto.getChanges());
            EditorChanges editorChanges = bookPublishingService.requestChanges(dto, bookId);
            formService.submitTaskForm(taskId, dto.toMap());
            return new ResponseEntity<>(new EditorBookDTO(editorChanges.getBook(), taskId, ""), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }*/

    @PostMapping(value = "upload_manuscript_final/{taskId}/{bookId}")
    public ResponseEntity<Object> uploadManuscriptFinal(@RequestPart("manuscript") MultipartFile[] manuscriptList, @PathVariable("taskId") String taskId, @PathVariable("bookId") String bookId) {
        MultipartFile manuscript = null;
        for (MultipartFile book : manuscriptList) {
            manuscript = book;
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();

        ArrayList<String> booksList = new ArrayList<>();
        try {
            String path = storageService.storeAsTempFile(manuscript);
            BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(processInstanceId);
            bookPublishingProcess.getBook().setFilePath(path);
            bookService.save(bookPublishingProcess.getBook());
            booksList.add(path);
        } catch (IOException e) {
            return new ResponseEntity<>("Could not store files", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        runtimeService.setVariable(processInstanceId, "bookList", booksList);

        try {
            String bookName = manuscript.getName();

            HashMap<String, Object> formFieldMap = new HashMap<>();
            formFieldMap.put("manuscript", bookName);

            formService.submitTaskForm(taskId, formFieldMap);
            String editor = (String) runtimeService.getVariable(processInstanceId, "editor");
            Task task2 = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName("Decide if suitable for publishing").singleResult();
            task2.setAssignee(editor);
            taskService.saveTask(task2);
            bookPublishingService.uploadManuscript(bookId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (FormFieldValidatorException | NullPointerException | BpmnError ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "betaReaderReviews/{bookId}")
    public ResponseEntity<List<BetaReaderReviewDTO>> getBetaReaderReviews(@PathVariable("bookId") String bookId) {
        List<BetaReaderReview> reviews = betaReaderService.viewReviews(bookId);
        List<BetaReaderReviewDTO> dto = reviews.stream().map(BetaReaderReviewDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "lectorReviews/{bookId}")
    public ResponseEntity<List<LectorReviewDTO>> getLectorReviews(@PathVariable("bookId") String bookId) throws Exception {
        List<LectorReview> reviews = lectorService.viewLectorNotes(bookId);
        List<LectorReviewDTO> dto = reviews.stream().map(LectorReviewDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "editorChanges/{bookId}")
    public ResponseEntity<List<EditorChangesDTO>> getEditorReviews(@PathVariable("bookId") String bookId) throws Exception {
        List<EditorChanges> reviews = bookPublishingService.getEditorChanges(bookId);
        List<EditorChangesDTO> dto = reviews.stream().map(editorChanges -> new EditorChangesDTO(editorChanges.getBook().getId(), editorChanges.getText())).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "readerBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookPreviewDTO>> getReadersBooks() {
        List<Book> books = bookPublishingService.getReaderBooks();
        List<BookPreviewDTO> dto = books.stream().map(BookPreviewDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "lectorBooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookPreviewDTO>> getLectorBooks() {
        List<Book> books = bookPublishingService.getLectorBooks();
        List<BookPreviewDTO> dto = books.stream().map(BookPreviewDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


}

