package lu.ftn.kpservice.repository;

import lu.ftn.kpservice.model.entity.BankLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BankLogRepository extends MongoRepository<BankLog, String> {

    List<BankLog> findAllByDateAfterAndDateBefore(Date from, Date to);

    List<BankLog> findAllByStatus(String status);
}
