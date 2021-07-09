package lu.ftn.model.dto;

import lu.ftn.model.entity.LectorReview;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

public class LectorReviewDTO {
    @NotNull
    @NotBlank
    private String lectorComment;
    @NotNull
    @NotBlank
    private String needsCorrections;

    public LectorReviewDTO() {
    }
    public LectorReviewDTO(LectorReview review) {
        this.lectorComment = review.getReview();
        if(review.isPositive()){
            this.needsCorrections = "NO";
        }else{
            this.needsCorrections = "YES";
        }
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("lectorComment", lectorComment);
        map.put("needsCorrections", needsCorrections);
        return map;
    }
    public LectorReviewDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "lectorComment": this.lectorComment = (String) submissionDTO.getFieldValue(); break;
                case "needsCorrections": this.needsCorrections = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public String getLectorComment() {
        return lectorComment;
    }

    public void setLectorComment(String lectorComment) {
        this.lectorComment = lectorComment;
    }

    public String getNeedsCorrections() {
        return needsCorrections;
    }

    public void setNeedsCorrections(String needsCorrections) {
        this.needsCorrections = needsCorrections;
    }
}
