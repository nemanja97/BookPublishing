package lu.ftn.model.dto;

import java.util.HashMap;
import java.util.List;

public class BetaReaderReviewSubmissionDTO {
    private String betaReaderComment;

    public BetaReaderReviewSubmissionDTO() {
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("betaReaderComment", betaReaderComment);
        return map;
    }

    public BetaReaderReviewSubmissionDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "betaReaderComment": this.betaReaderComment = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }


    public String getBetaReaderComment() {
        return betaReaderComment;
    }

    public void setBetaReaderComment(String betaReaderComment) {
        this.betaReaderComment = betaReaderComment;
    }
}
