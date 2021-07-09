package lu.ftn.services.tasks;

import lu.ftn.model.dto.PlagiarismBlameDTO;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.PlagiarismProcess;
import lu.ftn.model.entity.Writer;
import lu.ftn.services.BookService;
import lu.ftn.services.PlagiarismProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SavePlagiarismBlame  implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    PlagiarismProcessService plagiarismProcessService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - SavePlagiarismBlame");

        PlagiarismBlameDTO plagiarismBlameDTO = (PlagiarismBlameDTO) delegateExecution.getVariable("blame_form");

        Book plagiarismBook = returnBookOrThrowBPMNError(plagiarismBlameDTO.getPlagiarism_book(), plagiarismBlameDTO.getPlagiarism_author());
        Writer plagiarismWriter = plagiarismBook.getWriter();

        Book originalBook = returnBookOrThrowBPMNError(plagiarismBlameDTO.getOriginal_book(), plagiarismBlameDTO.getOriginal_author());
        Writer originalWriter = originalBook.getWriter();

        PlagiarismProcess plagiarismProcess = new PlagiarismProcess(
            plagiarismWriter, plagiarismBook, originalWriter, originalBook
        );

        try {
            plagiarismProcess = plagiarismProcessService.save(plagiarismProcess);
            delegateExecution.setVariable("processId", plagiarismProcess.getId());
        } catch (Exception ex) {
            throw new BpmnError(ex.getMessage());
        }

    }

    private Book returnBookOrThrowBPMNError(String title, String writerInfo) {
        String writerName = getWriterNameFromInfo(writerInfo);
        String writerSurname = getWriterSurnameFromInfo(writerInfo);

        Book book = bookService.findByTitleAndWriterInfo(title, writerName, writerSurname);
        if (book == null) {
            throw new BpmnError("Book not found");
        }

        return book;
    }

    private String getWriterNameFromInfo(String writerInfo) {
        return writerInfo.split(" ")[0];
    }

    private String getWriterSurnameFromInfo(String writerInfo) {
        return writerInfo.substring(writerInfo.split(" ")[0].length() + 1);
    }
}
