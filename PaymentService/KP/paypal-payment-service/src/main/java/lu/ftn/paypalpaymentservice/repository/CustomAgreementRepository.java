package lu.ftn.paypalpaymentservice.repository;

import lu.ftn.paypalpaymentservice.model.CustomAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomAgreementRepository extends JpaRepository<CustomAgreement, Long> {

    CustomAgreement findByToken(String token);
}
