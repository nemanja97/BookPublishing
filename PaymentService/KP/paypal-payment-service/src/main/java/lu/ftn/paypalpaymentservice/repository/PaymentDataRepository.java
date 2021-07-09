package lu.ftn.paypalpaymentservice.repository;

import lu.ftn.paypalpaymentservice.model.PaymentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDataRepository extends JpaRepository<PaymentData, String> {
}
