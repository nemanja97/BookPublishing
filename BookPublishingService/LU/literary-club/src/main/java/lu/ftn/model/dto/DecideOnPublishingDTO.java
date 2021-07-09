package lu.ftn.model.dto;

import java.util.HashMap;
import java.util.List;

public class DecideOnPublishingDTO {
    private String publish;

    public DecideOnPublishingDTO() {
    }

    public DecideOnPublishingDTO(String publish) {
        this.publish = publish;
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("shouldBePublished", publish);
        return map;
    }

    public DecideOnPublishingDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "shouldBePublished": this.publish = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }
}
