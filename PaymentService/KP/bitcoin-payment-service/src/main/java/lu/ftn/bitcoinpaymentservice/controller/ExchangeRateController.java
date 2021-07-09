package lu.ftn.bitcoinpaymentservice.controller;

import com.bitpay.sdk_light.BitPayException;
import lu.ftn.bitcoinpaymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/exchange_rates")
public class ExchangeRateController {

    @Autowired
    PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getExchangeRate(
            @RequestParam(value = "storeId") String storeId,
            @RequestParam(value = "currency", defaultValue = "EUR") String currency) throws BitPayException {
        return new ResponseEntity<>(paymentService.getExchangeRate(currency, storeId), HttpStatus.OK);
    }
}
