package lu.ftn.pccpaymentservice.controller;

import lu.ftn.pccpaymentservice.model.dto.PCCRequestDTO;
import lu.ftn.pccpaymentservice.model.dto.PCCResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api/pcc")
@CrossOrigin
public class PccController {

    @Bean("bankPaymentRestTemplate")
    public RestTemplate bankPaymentRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    @Qualifier("bankPaymentRestTemplate")
    private RestTemplate bankPaymentRestTemplate;

    @Value("${api.bank1}")
    private String bank1Api;

    @Value("${api.bank2}")
    private String bank2Api;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity passPaymentInformationToBuyersBank(@RequestBody PCCRequestDTO requestDTO) {
        String bankIdentificationNumber = requestDTO.getPanNumber().subSequence(0, 5).toString();

        if (bankIdentificationNumber.startsWith("4")) {
            try {
                ResponseEntity<Object> response = bankPaymentRestTemplate.exchange(
                        String.format("%s/api/payment/pcc", bank1Api), HttpMethod.PUT,
                        new HttpEntity<>(requestDTO), Object.class);
                return new ResponseEntity(response, response.getStatusCode());
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                return new ResponseEntity(ex.getStatusCode());
            }

        } else if (bankIdentificationNumber.startsWith("5")) {
            ResponseEntity<PCCResponseDTO> response = bankPaymentRestTemplate.exchange(
                    String.format("%s/api/payment/pcc", bank2Api), HttpMethod.PUT,
                    new HttpEntity<>(requestDTO), PCCResponseDTO.class);
            return new ResponseEntity(response, response.getStatusCode());
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
