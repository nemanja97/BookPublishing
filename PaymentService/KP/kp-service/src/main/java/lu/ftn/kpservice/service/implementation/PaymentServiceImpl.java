package lu.ftn.kpservice.service.implementation;

import lu.ftn.kpservice.exceptions.EntityNotFoundException;
import lu.ftn.kpservice.model.dto.BankPaymentDTO;
import lu.ftn.kpservice.model.dto.BitcoinServiceAccountInfoDTO;
import lu.ftn.kpservice.model.dto.PayPalPaymentDTO;
import lu.ftn.kpservice.model.dto.PaypalServiceAccountInfoDTO;
import lu.ftn.kpservice.model.entity.Store;
import lu.ftn.kpservice.model.entity.User;
import lu.ftn.kpservice.model.enums.PaymentType;
import lu.ftn.kpservice.service.PaymentService;
import lu.ftn.kpservice.service.StoreService;
import lu.ftn.kpservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    StoreService storeService;

    @Autowired
    UserService userService;

    @LoadBalanced
    @Bean
    public RestTemplate paymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("paymentRestTemplate")
    private RestTemplate paymentRestTemplate;

    @Override
    public HttpStatus addPaymentTypeBank(String userId, String merchantId, String merchantPassword, String bankName) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        BankPaymentDTO dto = new BankPaymentDTO(store.getId(), bankName, merchantId, merchantPassword);
        storeService.addPaymentOption(store.getId(), PaymentType.CARD);
        ResponseEntity response = paymentRestTemplate.exchange(
                "https://gateway-service/bank-payment-service/api/accounts/", HttpMethod.POST,
                new HttpEntity<>(dto),  ResponseEntity.class);

        return response.getStatusCode();
    }

    @Override
    public HttpStatus addPaymentTypePaypal(String userId, PayPalPaymentDTO dto) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        // TODO Implement properly
        storeService.addPaymentOption(store.getId(), PaymentType.PAYPAL);
        ResponseEntity response = paymentRestTemplate.exchange("https://gateway-service/paypal-payment-service/api/accounts", HttpMethod.POST,
                new HttpEntity<>(new PaypalServiceAccountInfoDTO(store.getId(),dto.getClientId(),dto.getClientSecret(), "")), ResponseEntity.class);
        return response.getStatusCode();
    }

    @Override
    public HttpStatus addPaymentTypeBitcoin(String userId, String token, String pairingCode) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        storeService.addPaymentOption(store.getId(), PaymentType.BITCOIN);
        ResponseEntity response = paymentRestTemplate.exchange("https://gateway-service/bitcoin-payment-service/api/accounts", HttpMethod.POST,
                new HttpEntity<>(new BitcoinServiceAccountInfoDTO(store.getId(), token, pairingCode)), ResponseEntity.class);

        return response.getStatusCode();
    }

    @Override
    public void removePaymentTypeBank(String userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        storeService.addPaymentOption(store.getId(), PaymentType.CARD);
        paymentRestTemplate.delete(String.format("https://gateway-service/bank-payment-service/api/account/%s", store.getId()));
    }

    @Override
    public void removePaymentTypePaypal(String userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        storeService.addPaymentOption(store.getId(), PaymentType.PAYPAL);
        paymentRestTemplate.delete(String.format("https://gateway-service/paypal-payment-service/api/account/%s", store.getId()));
    }

    @Override
    public void removePaymentTypeBitcoin(String userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        storeService.addPaymentOption(store.getId(), PaymentType.BITCOIN);
        paymentRestTemplate.delete(String.format("https://gateway-service/bitcoin-payment-service/api/account/%s", store.getId()));
    }
    @Override
    public List<String> getStorePaymentMethods(String userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        return store.getChosenPaymentTypes().stream().map(Enum::toString).collect(Collectors.toList());
    }
    @Override
    public Store getStoreForUser(String userId) {
        User user = userService.getUserById(userId);
        if (user == null)
            throw new EntityNotFoundException("User");

        Store store = user.getManagedStore();
        if (store == null)
            throw new EntityNotFoundException("Store");

        return store;
    }
}
