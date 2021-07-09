package lu.ftn.kpservice.repository;

import lu.ftn.kpservice.model.entity.BitcoinLog;
import lu.ftn.kpservice.model.entity.PaypalLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaypalLogRepository extends MongoRepository<PaypalLog, String> {

    List<PaypalLog> findAllByDateAfterAndDateBefore(Date from, Date to);

    List<PaypalLog> findAllByStatus(String status);
}
