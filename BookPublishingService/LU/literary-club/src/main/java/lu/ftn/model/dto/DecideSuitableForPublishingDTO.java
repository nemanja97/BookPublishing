package lu.ftn.model.dto;

import java.util.HashMap;
import java.util.List;

public class DecideSuitableForPublishingDTO {
    private String suitable;

    public DecideSuitableForPublishingDTO() {
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("suitable", suitable);
        return map;
    }

    public DecideSuitableForPublishingDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "suitable":
                    this.suitable = (String) submissionDTO.getFieldValue();
                    break;
            }
        }
    }

    public String getSuitable() {
        return suitable;
    }

    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }
}
