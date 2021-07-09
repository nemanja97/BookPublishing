package lu.ftn.kpservice.repository;

import lu.ftn.kpservice.model.entity.BitcoinLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BitcoinLogRepository  extends MongoRepository<BitcoinLog, String> {

    List<BitcoinLog> findAllByDateAfterAndDateBefore(Date from, Date to);

    List<BitcoinLog> findAllByStatus(String status);
}
