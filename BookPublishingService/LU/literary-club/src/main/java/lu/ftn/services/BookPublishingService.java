package lu.ftn.services;

import lu.ftn.model.dto.*;
import lu.ftn.model.entity.Book;
import lu.ftn.model.entity.EditorChanges;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookPublishingService {
    Book createBook(BookPublishingDTO dto);

    Book assignEditor(Book book);

    void notifyEditor(String email);

    Book editorReviewsBookPreview(boolean suitable, String bookId, String process) throws BpmnError;

    void notifyWriterAboutSubmissionDenial(String email);

    Book uploadManuscript(String bookId);

    Book decideOnPublishing(String bookId, boolean publish);

    Book decideIfSubmissionNeedsBetaReaders(String bookId, boolean needsBetaReaders);

    Book decideIfSubmissionNeedsChanges(String bookId, boolean needsChanges);

    void notifyWriterAboutLectorNotes(String email);

    EditorChanges requestChanges(EditorChangesDTO dto) throws Exception;

    Book publishBook(String bookId) throws Exception;

    public List<Book> getEditorBooks();

    List<Book> getWriterBooks();

    Book getBook(String id);

    Book decideOnPlagiarism(String bookId, boolean value);

    Book decideSuitableForPublishing(String bookId, boolean suitable);

    EditorChanges requestChanges(RequestEditorChangesDTO dto, String bookId) throws Exception;

    List<EditorChanges> getEditorChanges(String bookId);

    List<Book> getLectorBooks();

    List<Book> getReaderBooks();

    void deleteById(String id);
}
