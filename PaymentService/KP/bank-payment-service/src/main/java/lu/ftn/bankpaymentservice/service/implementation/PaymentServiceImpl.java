package lu.ftn.bankpaymentservice.service.implementation;

import lu.ftn.bankpaymentservice.exception.EntityNotFoundException;
import lu.ftn.bankpaymentservice.model.dto.InvoiceResponseDTO;
import lu.ftn.bankpaymentservice.model.entity.AccountInfo;
import lu.ftn.bankpaymentservice.model.entity.Invoice;
import lu.ftn.bankpaymentservice.service.AccountService;
import lu.ftn.bankpaymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Bean("bankInvoicePaymentRestTemplate")
    public RestTemplate bankInvoicePaymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("bankInvoicePaymentRestTemplate")
    private RestTemplate bankInvoicePaymentRestTemplate;

    @Autowired
    private AccountService accountService;

    public ResponseEntity<InvoiceResponseDTO> createBankPaymentFromInvoice(String merchantOrderId, String storeId, String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl, BigDecimal amount, String currency) {
        AccountInfo info = accountService.get(storeId);
        if (info == null)
            throw new EntityNotFoundException("Account");

        Invoice invoice = new Invoice(
                info.getMerchantId(),
                info.getMerchantPassword(),
                amount, currency,
                merchantOrderId,
                LocalDateTime.now(),
                successRedirectUrl, failureRedirectUrl, errorRedirectUrl
                );

        try {
            return bankInvoicePaymentRestTemplate.exchange(
                    info.getBankTransactionEndpoint(), HttpMethod.POST,
                    new HttpEntity<>(invoice), InvoiceResponseDTO.class);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
