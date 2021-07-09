package lu.ftn.paypalpaymentservice.helper;

import lu.ftn.paypalpaymentservice.model.CustomBillingPlan;
import lu.ftn.paypalpaymentservice.model.dto.SubscriptionPlanDTO;
import lu.ftn.paypalpaymentservice.model.dto.TransactionResponseDTO;

public class Converter {
    public static SubscriptionPlanDTO createSubscriptionPlanDTO(CustomBillingPlan plan) {
        SubscriptionPlanDTO dto = new SubscriptionPlanDTO();
        dto.setAmount(String.valueOf(plan.getAmount()));
        dto.setCurrency(plan.getCurrency());
        dto.setCycles(plan.getCycles());
        dto.setDescription(plan.getDescription());
        dto.setAmountStart(String.valueOf(plan.getAmountStart()));
        dto.setFrequency(plan.getFrequency());
        dto.setName(plan.getName());
        dto.setFreqInterval(plan.getFrequencyInterval());
        dto.setStoreId(plan.getStoreId());
        dto.setId(plan.getId());
        return dto;
    }
}
