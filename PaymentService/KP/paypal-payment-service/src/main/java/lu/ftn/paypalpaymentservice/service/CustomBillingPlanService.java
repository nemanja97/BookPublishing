package lu.ftn.paypalpaymentservice.service;

import lu.ftn.paypalpaymentservice.model.CustomBillingPlan;
import lu.ftn.paypalpaymentservice.repository.CustomBillingPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomBillingPlanService {

    private final CustomBillingPlanRepository customBillingPlanRepository;

    public CustomBillingPlanService(CustomBillingPlanRepository customBillingPlanRepository) {
        this.customBillingPlanRepository = customBillingPlanRepository;
    }

    public CustomBillingPlan findOne(Long id) {
        return customBillingPlanRepository.getOne(id);
    }

    public CustomBillingPlan save(CustomBillingPlan plan) {
        return customBillingPlanRepository.save(plan);
    }

    public List<CustomBillingPlan> findAllByStoreId(String storeId) {
        return customBillingPlanRepository.findAllByStoreId(storeId);
    }

}
