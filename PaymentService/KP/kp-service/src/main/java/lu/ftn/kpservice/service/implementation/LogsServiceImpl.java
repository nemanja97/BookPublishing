package lu.ftn.kpservice.service.implementation;

import lu.ftn.kpservice.model.entity.BankLog;
import lu.ftn.kpservice.model.entity.BitcoinLog;
import lu.ftn.kpservice.model.entity.PaypalLog;
import lu.ftn.kpservice.repository.BankLogRepository;
import lu.ftn.kpservice.repository.BitcoinLogRepository;
import lu.ftn.kpservice.repository.PaypalLogRepository;
import lu.ftn.kpservice.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogsServiceImpl implements LogsService {

    private final PaypalLogRepository paypalLogRepository;

    private final BitcoinLogRepository bitcoinLogRepository;

    private final BankLogRepository bankLogRepository;

    public LogsServiceImpl(PaypalLogRepository paypalLogRepository,
                           BitcoinLogRepository bitcoinLogRepository,
                           BankLogRepository bankLogRepository) {
        this.paypalLogRepository = paypalLogRepository;
        this.bitcoinLogRepository = bitcoinLogRepository;
        this.bankLogRepository = bankLogRepository;
    }

    @Override
    public BankLog saveBankLog(BankLog log) {
        return bankLogRepository.save(log);
    }

    @Override
    public PaypalLog savePaypalLog(PaypalLog log) {
        return paypalLogRepository.save(log);
    }

    @Override
    public List<PaypalLog> getAllPaypalLogs() {
        return paypalLogRepository.findAll();
    }

    @Override
    public PaypalLog getPaypalLog(String id) {
        return paypalLogRepository.findById(id).orElse(null);
    }

    @Override
    public List<PaypalLog> getAllPaypalLogsWhereStatus(String status) {
        return paypalLogRepository.findAllByStatus(status);
    }

    @Override
    public List<PaypalLog> getPaypalLogsBetween(Date from, Date to) {
        return paypalLogRepository.findAllByDateAfterAndDateBefore(from, to);
    }

    @Override
    public BitcoinLog saveBitcoinLog(BitcoinLog log) {
        return bitcoinLogRepository.save(log);
    }

    @Override
    public List<BitcoinLog> getAllBitcoinLogs() {
        return bitcoinLogRepository.findAll();
    }

    @Override
    public BitcoinLog getBitcoinLog(String id) {
        return bitcoinLogRepository.findById(id).orElse(null);
    }

    @Override
    public List<BitcoinLog> getAllBitcoinLogsWhereStatus(String status) {
        return bitcoinLogRepository.findAllByStatus(status);
    }

    @Override
    public List<BitcoinLog> getBitcoinLogsBetween(Date from, Date to) {
        return bitcoinLogRepository.findAllByDateAfterAndDateBefore(from, to);
    }
}
