package lu.ftn.model.dto;

import lu.ftn.model.entity.PlagiarismVote;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class EditorPlagiarismVoteDTO implements Serializable {

    @NotNull
    private PlagiarismVote plagiarism_vote;

    public EditorPlagiarismVoteDTO() {
    }

    public EditorPlagiarismVoteDTO(@NotNull PlagiarismVote plagiarism_vote) {
        this.plagiarism_vote = plagiarism_vote;
    }

    public EditorPlagiarismVoteDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "plagiarism_vote": this.plagiarism_vote = PlagiarismVote.valueOf((String) submissionDTO.getFieldValue()); break;
            }
        }
    }

    public PlagiarismVote getPlagiarism_vote() {
        return plagiarism_vote;
    }

    public void setPlagiarism_vote(PlagiarismVote plagiarism_vote) {
        this.plagiarism_vote = plagiarism_vote;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("plagiarism_vote", this.plagiarism_vote.toString());
        return map;
    }

}
