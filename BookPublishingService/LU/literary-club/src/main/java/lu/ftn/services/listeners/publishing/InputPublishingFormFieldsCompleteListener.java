package lu.ftn.services.listeners.publishing;

import lu.ftn.model.dto.BookPublishingDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.BookPublishingProcess;
import lu.ftn.repository.BookPublishingProcessRepository;
import lu.ftn.services.BookPublishingService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InputPublishingFormFieldsCompleteListener implements TaskListener {
    @Autowired
    BookPublishingProcessRepository bookPublishingProcessRepository;

    @Autowired
    BookPublishingService bookPublishingService;

    @Override
    public void notify(DelegateTask delegateTask) {
        List<FormSubmissionDTO> originalFormSubmissionValues = (List<FormSubmissionDTO>) delegateTask.getVariable("originalFormSubmissionValues");
        BookPublishingDTO bookDTO = new BookPublishingDTO(originalFormSubmissionValues);
        Book book = bookPublishingService.createBook(bookDTO);
        BookPublishingProcess bookPublishingProcess = new BookPublishingProcess(delegateTask.getProcessInstanceId(), book, null, book.getWriter());
        bookPublishingProcessRepository.save(bookPublishingProcess);
        delegateTask.setVariable("writer", book.getWriter().getId());
        System.out.println("InputPublishingFormFieldsCompleteListener");
    }
}
