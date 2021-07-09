package lu.ftn.services.implementation;

import lu.ftn.model.entity.*;
import lu.ftn.repository.PlagiarismProcessRepository;
import lu.ftn.services.BookService;
import lu.ftn.services.PlagiarismProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlagiarismProcessServiceImpl implements PlagiarismProcessService {

    @Autowired
    PlagiarismProcessRepository processRepository;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    @Override
    public PlagiarismProcess save(PlagiarismProcess plagiarismProcess) {
        return processRepository.save(plagiarismProcess);
    }

    @Override
    public PlagiarismProcess findById(String id) {
        return processRepository.findById(id).orElse(null);
    }

    @Override
    public PlagiarismProcess leaveNote(String plagiarismId, String userId, String note) {
        PlagiarismProcess process = findById(plagiarismId);
        if (process == null)
            throw new BpmnError("Process not found");

        User user = userService.findOne(userId);
        if (user == null)
            throw new BpmnError("Editor not found");

        if (!(user.getRole().equals(Role.ROLE_EDITOR) || user.getRole().equals(Role.ROLE_HEAD_EDITOR)))
            throw new BpmnError("User not editor");

        try {
            process.getNotes().add(new PlagiarismProcessNote(user, note, process));
            return save(process);
        } catch (Exception ex) {
            throw new BpmnError("Error saving editor note");
        }

    }

    @Override
    public PlagiarismProcess castVote(String plagiarismId, String userId, PlagiarismVote vote) {
        PlagiarismProcess process = findById(plagiarismId);
        if (process == null)
            throw new BpmnError("Process not found");

        User user = userService.findOne(userId);
        if (user == null)
            throw new BpmnError("Editor not found");

        if (!(user.getRole().equals(Role.ROLE_EDITOR) || user.getRole().equals(Role.ROLE_HEAD_EDITOR)))
            throw new BpmnError("User not editor");

        try {
            process.getVotes().add(new PlagiarismProcessVote(user, vote, process));
            return save(process);
        } catch (Exception ex) {
            throw new BpmnError("Error saving editor note");
        }
    }

    @Override
    public PlagiarismProcess decideOnOutcome(String plagiarismId) {
        PlagiarismProcess process = findById(plagiarismId);
        if (process == null)
            throw new BpmnError("Process not found");

        try {
            if (process.getVotes().stream().map(PlagiarismProcessVote::getVote).allMatch(vote -> vote.equals(PlagiarismVote.PLAGIARISM))) {
                process.setDecision(PlagiarismDecision.PLAGIARISM);
                return save(process);
            }

            if (process.getVotes().stream().map(PlagiarismProcessVote::getVote).allMatch(vote -> vote.equals(PlagiarismVote.NOT_PLAGIARISM))) {
                process.setDecision(PlagiarismDecision.NOT_PLAGIARISM);
                return save(process);
            }

            process.setDecision(PlagiarismDecision.NO_CONSENSUS);
            return save(process);
        } catch (Exception ex) {
            throw new BpmnError("Error saving decision");
        }

    }

    @Override
    public void takedownBook(String plagiarismId) {
        PlagiarismProcess process = findById(plagiarismId);
        if (process == null)
            throw new BpmnError("Process not found");

        Book bookToTakedown = process.getBlamedBook();
        if (bookToTakedown == null)
            throw new BpmnError("Book not found");

        bookToTakedown.setStatus(BookStatus.TAKEDOWN_PLAGIARISM);
        bookService.save(bookToTakedown);
    }

    @Override
    public void deleteById(String id) {
        processRepository.deleteById(id);
    }
}
