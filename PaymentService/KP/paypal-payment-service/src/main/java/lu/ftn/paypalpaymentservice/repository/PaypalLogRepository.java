package lu.ftn.paypalpaymentservice.repository;

import lu.ftn.paypalpaymentservice.model.PaypalLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaypalLogRepository extends MongoRepository<PaypalLog, String> {
}
