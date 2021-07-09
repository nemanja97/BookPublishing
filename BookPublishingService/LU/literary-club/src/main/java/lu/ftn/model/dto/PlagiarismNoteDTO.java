package lu.ftn.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlagiarismNoteDTO implements Serializable {

    @NotNull
    @NotBlank
    private String notes;

    public PlagiarismNoteDTO() {
    }

    public PlagiarismNoteDTO(@NotNull @NotBlank String notes) {
        this.notes = notes;
    }

    public PlagiarismNoteDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "notes": this.notes = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("notes", notes);
        return map;
    }
}
