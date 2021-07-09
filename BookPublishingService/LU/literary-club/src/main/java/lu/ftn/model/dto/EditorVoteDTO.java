package lu.ftn.model.dto;

import lu.ftn.model.entity.MembershipVote;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class EditorVoteDTO implements Serializable {

    @NotNull
    private MembershipVote editor_vote;

    public EditorVoteDTO() {
    }

    public EditorVoteDTO(@NotNull MembershipVote editor_vote) {
        this.editor_vote = editor_vote;
    }

    public EditorVoteDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "editor_vote": this.editor_vote = MembershipVote.valueOf((String) submissionDTO.getFieldValue()); break;
            }
        }
    }

    public MembershipVote getEditor_vote() {
        return editor_vote;
    }

    public void setEditor_vote(MembershipVote editor_vote) {
        this.editor_vote = editor_vote;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("editor_vote", this.editor_vote.toString());
        return map;
    }
}
