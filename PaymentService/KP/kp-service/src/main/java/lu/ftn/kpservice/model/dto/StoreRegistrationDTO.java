package lu.ftn.kpservice.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StoreRegistrationDTO {

    @NotNull
    @NotBlank
    private String name;

    private String parentStoreId;

    private String storeTransactionSuccessWebhook;

    private String storeTransactionFailureWebhook;

    private String storeTransactionErrorWebhook;

    public StoreRegistrationDTO(@NotNull @NotBlank String name, String parentStoreId, String storeTransactionSuccessWebhook, String storeTransactionFailureWebhook, String storeTransactionErrorWebhook) {
        this.name = name;
        this.parentStoreId = parentStoreId;
        this.storeTransactionSuccessWebhook = storeTransactionSuccessWebhook;
        this.storeTransactionFailureWebhook = storeTransactionFailureWebhook;
        this.storeTransactionErrorWebhook = storeTransactionErrorWebhook;
    }

    public StoreRegistrationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentStoreId() {
        return parentStoreId;
    }

    public void setParentStoreId(String parentStoreId) {
        this.parentStoreId = parentStoreId;
    }

    public String getStoreTransactionSuccessWebhook() {
        return storeTransactionSuccessWebhook;
    }

    public void setStoreTransactionSuccessWebhook(String storeTransactionSuccessWebhook) {
        this.storeTransactionSuccessWebhook = storeTransactionSuccessWebhook;
    }

    public String getStoreTransactionFailureWebhook() {
        return storeTransactionFailureWebhook;
    }

    public void setStoreTransactionFailureWebhook(String storeTransactionFailureWebhook) {
        this.storeTransactionFailureWebhook = storeTransactionFailureWebhook;
    }

    public String getStoreTransactionErrorWebhook() {
        return storeTransactionErrorWebhook;
    }

    public void setStoreTransactionErrorWebhook(String storeTransactionErrorWebhook) {
        this.storeTransactionErrorWebhook = storeTransactionErrorWebhook;
    }
}
