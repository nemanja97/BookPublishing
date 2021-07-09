package lu.ftn.model.dto;

import lu.ftn.model.entity.Genre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BookSuitabilityDTO implements Serializable {
    private String suitable;

    public BookSuitabilityDTO() {
    }
    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("suitable", suitable);
        return map;
    }
    public BookSuitabilityDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "suitable":{
                    this.suitable = (String) submissionDTO.getFieldValue();
                    break;
                }
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
