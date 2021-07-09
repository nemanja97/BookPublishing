package lu.ftn.paypalpaymentservice.repository;

import lu.ftn.paypalpaymentservice.model.CustomBillingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomBillingPlanRepository extends JpaRepository<CustomBillingPlan, Long> {

    public List<CustomBillingPlan> findAllByStoreId(String storeId);
}
