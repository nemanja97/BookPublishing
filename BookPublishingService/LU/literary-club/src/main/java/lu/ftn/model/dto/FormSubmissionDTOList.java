package lu.ftn.model.dto;

import java.util.List;

public class FormSubmissionDTOList {

    private List<FormSubmissionDTO> dtoList;

    public FormSubmissionDTOList() {
    }

    public FormSubmissionDTOList(List<FormSubmissionDTO> dtoList) {
        this.dtoList = dtoList;
    }

    public List<FormSubmissionDTO> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<FormSubmissionDTO> dtoList) {
        this.dtoList = dtoList;
    }
}
