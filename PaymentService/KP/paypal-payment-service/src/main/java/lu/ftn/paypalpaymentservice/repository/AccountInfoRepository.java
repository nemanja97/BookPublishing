package lu.ftn.paypalpaymentservice.repository;

import lu.ftn.paypalpaymentservice.model.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, String> {
}
