package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CreateInvoiceRequestForPlanDTO {
    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String storeId;
    private String planId;

    public CreateInvoiceRequestForPlanDTO() {
    }

    public CreateInvoiceRequestForPlanDTO(@NotNull @NotBlank String id, @NotNull @NotBlank String storeId, String planId) {
        this.id = id;
        this.storeId = storeId;
        this.planId = planId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
