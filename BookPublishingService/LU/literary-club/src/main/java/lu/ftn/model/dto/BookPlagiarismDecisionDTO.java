package lu.ftn.model.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class BookPlagiarismDecisionDTO implements Serializable {
    private String plagiarism;

    public BookPlagiarismDecisionDTO() {
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("plagiarism", plagiarism);
        return map;
    }
    public BookPlagiarismDecisionDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "plagiarism": this.plagiarism = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public String getPlagiarism() {
        return plagiarism;
    }

    public void setPlagiarism(String plagiarism) {
        this.plagiarism = plagiarism;
    }
}
