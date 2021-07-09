package lu.ftn.paypalpaymentservice.service;

import lu.ftn.paypalpaymentservice.model.PaypalLog;
import lu.ftn.paypalpaymentservice.repository.PaypalLogRepository;
import org.springframework.stereotype.Service;

@Service
public class PaypalLogService {

    private final PaypalLogRepository paypalLogRepository;

    public PaypalLogService(PaypalLogRepository paypalLogRepository) {
        this.paypalLogRepository = paypalLogRepository;
    }
    
    public PaypalLog saveLog(PaypalLog log){
        return paypalLogRepository.save(log);
    }
}
