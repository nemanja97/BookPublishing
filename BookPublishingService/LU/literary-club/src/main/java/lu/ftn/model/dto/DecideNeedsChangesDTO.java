package lu.ftn.model.dto;

import java.util.HashMap;
import java.util.List;

public class DecideNeedsChangesDTO {
    private String needsChanges;

    public DecideNeedsChangesDTO() {
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("needsChanges", needsChanges);
        return map;
    }

    public DecideNeedsChangesDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "needsChanges":
                    this.needsChanges = (String) submissionDTO.getFieldValue();
                    break;
            }
        }
    }

    public String getNeedsChanges() {
        return needsChanges;
    }

    public void setNeedsChanges(String needsChanges) {
        this.needsChanges = needsChanges;
    }
}
