package lu.ftn.model.dto;

import java.io.Serializable;

public class FormSubmissionDTO implements Serializable {

    String fieldId;
    Object fieldValue;

    public FormSubmissionDTO() {
        super();
    }

    public FormSubmissionDTO(String fieldId, Object fieldValue) {
        super();
        this.fieldId = fieldId;
        this.fieldValue = fieldValue;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

}