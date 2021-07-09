package lu.ftn.model.dto;

import lu.ftn.model.entity.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AssignBetaReadersDTO {

    private List<String> reviewerUsernames;

    public AssignBetaReadersDTO() {
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("betareaders", String.join(",", reviewerUsernames));
        return map;
    }

    public AssignBetaReadersDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "betareaders":{
                    this.reviewerUsernames = ((List<String>) submissionDTO.getFieldValue()).stream().collect(Collectors.toList());
                    break;
                }
            }
        }
    }

    public List<String> getReviewerUsernames() {
        return reviewerUsernames;
    }

    public void setReviewerUsernames(List<String> reviewerUsernames) {
        this.reviewerUsernames = reviewerUsernames;
    }
}
