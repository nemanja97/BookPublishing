package lu.ftn.services;

import lu.ftn.model.entity.PlagiarismProcess;
import lu.ftn.model.entity.PlagiarismVote;

public interface PlagiarismProcessService {

    PlagiarismProcess save(PlagiarismProcess plagiarismProcess);

    PlagiarismProcess findById(String id);

    PlagiarismProcess leaveNote(String plagiarismId, String userId, String note);

    PlagiarismProcess castVote(String plagiarismId, String userId, PlagiarismVote vote);

    PlagiarismProcess decideOnOutcome(String plagiarismId);

    void takedownBook(String plagiarismId);

    void deleteById(String id);

}
