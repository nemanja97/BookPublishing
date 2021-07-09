package lu.ftn.model.dto;

import java.util.HashMap;
import java.util.List;

public class RequestEditorChangesDTO {
    private String changes;

    public RequestEditorChangesDTO() {
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("changes", changes);
        return map;
    }

    public RequestEditorChangesDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "changes": this.changes = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public RequestEditorChangesDTO(String changes) {
        this.changes = changes;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }
}
