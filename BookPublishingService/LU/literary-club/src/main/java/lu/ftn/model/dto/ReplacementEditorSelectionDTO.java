package lu.ftn.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class ReplacementEditorSelectionDTO implements Serializable {

    @NotNull
    @NotBlank
    private String replacement_editor;

    public ReplacementEditorSelectionDTO() {
    }

    public ReplacementEditorSelectionDTO(@NotNull @NotBlank String replacement_editor) {
        this.replacement_editor = replacement_editor;
    }

    public ReplacementEditorSelectionDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "replacement_editor": this.replacement_editor = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public String getReplacement_editor() {
        return replacement_editor;
    }

    public void setReplacement_editor(String replacement_editor) {
        this.replacement_editor = replacement_editor;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("replacement_editor", this.replacement_editor);
        return map;
    }
}
