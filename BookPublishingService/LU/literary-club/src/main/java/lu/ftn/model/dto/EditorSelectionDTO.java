package lu.ftn.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorSelectionDTO implements Serializable {

    List<String> chosen_editors;

    public EditorSelectionDTO() {
        this.chosen_editors = new ArrayList<>();
    }

    public EditorSelectionDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "chosen_editors": this.chosen_editors = (List<String>) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public List<String> getChosen_editors() {
        return chosen_editors;
    }

    public void setChosen_editors(List<String> chosen_editors) {
        this.chosen_editors = chosen_editors;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("chosen_editors", String.join(",", this.chosen_editors));
        return map;
    }
}
