package lu.ftn.services.tasks;

import lu.ftn.model.dto.PlagiarismBlameDTO;
import lu.ftn.model.entity.Book;
import lu.ftn.services.BookService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatePlagiarismBlameFields implements JavaDelegate {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("CAMUNDA - ValidatePlagiarismBlameFields");

        try {
            PlagiarismBlameDTO plagiarismBlameDTO = (PlagiarismBlameDTO) delegateExecution.getVariable("blame_form");
            validateFields(plagiarismBlameDTO);
            delegateExecution.setVariable("blameFormValid", true);
        } catch (FormFieldValidatorException ex) {
            delegateExecution.setVariable("blameFormValid", false);
            throw ex;
        }

    }

    private void validateFields(PlagiarismBlameDTO dto) {
        validateWriter("plagiarism", dto.getPlagiarism_author());
        validateBook("plagiarism", dto.getPlagiarism_book(), dto.getPlagiarism_author());

        validateWriter("original", dto.getOriginal_author());
        validateBook("original", dto.getOriginal_book(), dto.getOriginal_author());
    }

    private void validateWriter(String type, String authorInfo) {
        if (authorInfo.split(" ").length != 2) {
            throw new FormFieldValidatorException("1A", String.format("%s_author", type), "invalid", authorInfo, "Writer name or surname not given");
        }

        String authorName = authorInfo.split(" ")[0];
        if (!authorName.chars().allMatch(Character::isLetter)) {
            throw new FormFieldValidatorException("1B", String.format("%s_author", type), "invalid", authorInfo, "Writer name can contain only letters");
        }

        String authorSurname = authorInfo.substring(authorName.length() + 1);
        if (!authorSurname.chars().allMatch(Character::isLetter)) {
            throw new FormFieldValidatorException("1C", String.format("%s_author", type), "invalid", authorInfo, "Writer surname can contain only letters");
        }
    }

    private void validateBook(String type, String title, String authorInfo) {
        String authorName = authorInfo.split(" ")[0];
        String authorSurname = authorInfo.substring(authorName.length() + 1);

        Book book = bookService.findByTitleAndWriterInfo(title, authorName, authorSurname);
        if (book == null) {
            throw new FormFieldValidatorException("2A", String.format("%s_book", type), "invalid", title, "Unknown book");
        }
    }
}
