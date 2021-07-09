package lu.ftn.kpservice.service;

import lu.ftn.kpservice.model.dto.PayPalPaymentDTO;
import lu.ftn.kpservice.model.entity.Store;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface PaymentService {

    HttpStatus addPaymentTypeBank(String userId, String merchantId, String merchantPassword, String bankName);

    HttpStatus addPaymentTypePaypal(String userId, PayPalPaymentDTO dto);

    HttpStatus addPaymentTypeBitcoin(String userId, String token, String pairingCode);

    void removePaymentTypeBank(String userId);

    void removePaymentTypePaypal(String userId);

    void removePaymentTypeBitcoin(String userId);

    List<String> getStorePaymentMethods(String userId);

    Store getStoreForUser(String userId);
}
