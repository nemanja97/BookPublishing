package lu.ftn.bankpaymentservice.repository;

import lu.ftn.bankpaymentservice.model.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, String> {
}
