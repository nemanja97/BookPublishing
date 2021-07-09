package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.AssignBetaReadersDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.User;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BetaReaderService;
import lu.ftn.services.BookPublishingService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DecideOnBetaReadersCompleteListener implements TaskListener {

    private final BookPublishingService bookPublishingService;
    private final BookPublishingProcessRepository bookPublishingProcessRepository;
    private final BetaReaderService betaReaderService;
    private final UserService userService;
    private final TaskService taskService;
    public DecideOnBetaReadersCompleteListener(BookPublishingService bookPublishingService, BookPublishingProcessRepository bookPublishingProcessRepository, BetaReaderService betaReaderService, UserService userService, TaskService taskService) {
        this.bookPublishingService = bookPublishingService;
        this.bookPublishingProcessRepository = bookPublishingProcessRepository;
        this.betaReaderService = betaReaderService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        AssignBetaReadersDTO dto = new AssignBetaReadersDTO(originalFormSubmissionValues);
        BookPublishingProcess bookPublishingProcess = bookPublishingProcessRepository.findByProcessId(delegateTask.getProcessInstanceId());
        try {
            Book book = betaReaderService.assignBetaReaders(dto, bookPublishingProcess.getBook().getId());
            List<User> users = new ArrayList<>();
            for (String username : dto.getReviewerUsernames()) {
                users.add(userService.findUserByUsername(username));
            }
            List<String> userId = users.stream().map(user -> user.getId()).collect(Collectors.toList());
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(delegateTask.getProcessInstanceId()).taskName("Leave review").list();
            delegateTask.setVariable("selectedBetaReaders", userId);
            int i = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
