package lu.ftn.luaccountingservice.model.dto;

public class PaypalSubscriptionPlanDTO {
    private String id;
    private String name;
    private String description;
    private String frequency;
    private String freqInterval;
    private String cycles;
    private String amount;
    private String currency;
    private String amountStart;
    private String storeId;

    public PaypalSubscriptionPlanDTO() {
    }

    public PaypalSubscriptionPlanDTO(String name, String description, String frequency, String freqInterval, String cycles, String amount, String currency, String amountStart, String storeId) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.freqInterval = freqInterval;
        this.cycles = cycles;
        this.amount = amount;
        this.currency = currency;
        this.amountStart = amountStart;
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFreqInterval() {
        return freqInterval;
    }

    public void setFreqInterval(String freqInterval) {
        this.freqInterval = freqInterval;
    }

    public String getCycles() {
        return cycles;
    }

    public void setCycles(String cycles) {
        this.cycles = cycles;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmountStart() {
        return amountStart;
    }

    public void setAmountStart(String amountStart) {
        this.amountStart = amountStart;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
