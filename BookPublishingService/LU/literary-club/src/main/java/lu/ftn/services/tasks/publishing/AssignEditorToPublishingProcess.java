package lu.ftn.services.tasks.publishing;

import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

@Service
public class AssignEditorToPublishingProcess implements JavaDelegate {
    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    public AssignEditorToPublishingProcess(BookPublishingService bookPublishingService, BookPublishingProcessRepository bookPublishingProcessRepository, RuntimeService runtimeService, TaskService taskService) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("AssignEditorToPublishingProcess");
        BookPublishingProcess process = bookPublishingProcessRepository.findByProcessId(delegateExecution.getProcessInstanceId());
        Book book = bookPublishingService.assignEditor(process.getBook());
        process.setEditor(book.getEditor());
        runtimeService.setVariable(delegateExecution.getProcessInstanceId(), "editor", book.getEditor().getId());
        bookPublishingProcessRepository.save(process);

    }
}
