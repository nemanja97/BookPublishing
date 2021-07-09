package lu.ftn.kpservice.controller;

import lu.ftn.kpservice.model.dto.BankPaymentDTO;
import lu.ftn.kpservice.model.dto.BitcoinPaymentDTO;
import lu.ftn.kpservice.model.dto.PayPalPaymentDTO;
import lu.ftn.kpservice.model.entity.Store;
import lu.ftn.kpservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/payment")
public class PaymentMethodController {

    @Autowired
    PaymentService paymentService;

    @PostMapping(path = "card", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity addCardPaymentOption(@Valid @RequestBody BankPaymentDTO dto) {
        HttpStatus status = paymentService.addPaymentTypeBank(getCurrentUserId(), dto.getMerchantId(), dto.getMerchantPassword(), dto.getBankName());
        return new ResponseEntity<>(status);
    }

    @PostMapping(path = "paypal", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity addPaypalPaymentOption(@Valid @RequestBody PayPalPaymentDTO dto) {
        HttpStatus status = paymentService.addPaymentTypePaypal(getCurrentUserId(), dto);
        return new ResponseEntity<>(status);
    }

    @PostMapping(path = "bitcoin", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity addBitcoinPaymentOption(@Valid @RequestBody BitcoinPaymentDTO dto) {
        HttpStatus status = paymentService.addPaymentTypeBitcoin(getCurrentUserId(), dto.getToken(), dto.getPairingCode());
        return new ResponseEntity<>(status);
    }

    @DeleteMapping(path = "card", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity deleteCardPaymentOption() {
        paymentService.removePaymentTypeBank(getCurrentUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "paypal", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity deletePaypalPaymentOption() {
        paymentService.removePaymentTypePaypal(getCurrentUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "bitcoin", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity deleteBitcoinPaymentOption() {
        paymentService.removePaymentTypeBitcoin(getCurrentUserId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "options", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity<List<String>> getStorePaymentOptions() {
        List<String> options = paymentService.getStorePaymentMethods(getCurrentUserId());
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @GetMapping(path = "storeId", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity<String> getStoreId() {
        Store store = paymentService.getStoreForUser(getCurrentUserId());
        return new ResponseEntity<>(store.getId(), HttpStatus.OK);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
