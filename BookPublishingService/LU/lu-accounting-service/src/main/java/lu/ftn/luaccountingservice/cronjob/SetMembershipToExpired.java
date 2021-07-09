package lu.ftn.luaccountingservice.cronjob;

import lu.ftn.luaccountingservice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SetMembershipToExpired {

    @Autowired
    InvoiceService invoiceService;

    @Scheduled(cron = "0 23 59 * * ?")
    public void setMembershipToExpire() {
        invoiceService.setMembershipAsExpired();
    }
}
