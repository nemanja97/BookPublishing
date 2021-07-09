package lu.ftn.bitcoinpaymentservice.repository;

import lu.ftn.bitcoinpaymentservice.model.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, String> {
}
