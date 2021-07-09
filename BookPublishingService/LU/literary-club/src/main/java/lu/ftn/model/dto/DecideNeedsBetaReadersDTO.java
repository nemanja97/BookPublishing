package lu.ftn.model.dto;

import java.util.HashMap;
import java.util.List;

public class DecideNeedsBetaReadersDTO {
    private String needsBetaReaders;

    public DecideNeedsBetaReadersDTO() {
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("needsBetareaders", needsBetaReaders);
        return map;
    }

    public DecideNeedsBetaReadersDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "needsBetareaders": this.needsBetaReaders = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public String getNeedsBetaReaders() {
        return needsBetaReaders;
    }

    public void setNeedsBetaReaders(String needsBetaReaders) {
        this.needsBetaReaders = needsBetaReaders;
    }
}
