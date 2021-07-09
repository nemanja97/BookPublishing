package lu.ftn.services.tasks;

import lu.ftn.services.StorageService;
import org.apache.pdfbox.preflight.parser.PreflightParser;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ValidateFileContents implements JavaDelegate {

    @Autowired
    StorageService storageService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("CAMUNDA - ValidateFileContents");
        ArrayList<String> books = (ArrayList<String>) delegateExecution.getVariable("bookList");

        try {
            validateFiles(books);
            delegateExecution.setVariable("filesAreValid", true);
        } catch (FormFieldValidatorException ex) {
            delegateExecution.setVariable("filesAreValid", false);
            throw ex;
        }
    }

    private void validateFiles(ArrayList<String> books) {
        validateNumberOfFiles(books);
        for (String book : books) {
           checkIfFileIsPDF(book);
        }
    }

    private void validateNumberOfFiles(ArrayList<String> books) {
        if (books == null || books.size() < 2)
            throw new FormFieldValidatorException("1A", "books", "invalid", "books", "Must upload at least 2 books");
    }

    private void checkIfFileIsPDF(String book) {
        try {
            File tempFile = storageService.returnFile(book);
            PreflightParser parser = new PreflightParser(tempFile);

            parser.parse();
            parser.getPreflightDocument().validate();
            parser.getPreflightDocument().close();
        } catch (IOException e) {
            throw new FormFieldValidatorException("2A", "books", "invalid", "books", "File is not a valid PDF document");
        }
    }

}
