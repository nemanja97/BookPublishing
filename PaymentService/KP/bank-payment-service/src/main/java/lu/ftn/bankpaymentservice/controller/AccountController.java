package lu.ftn.bankpaymentservice.controller;

import lu.ftn.bankpaymentservice.model.dto.AccountInfoDTO;
import lu.ftn.bankpaymentservice.model.entity.AccountInfo;
import lu.ftn.bankpaymentservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/accounts")
@CrossOrigin
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(path = "/{storeId}", produces = "application/json")
    public ResponseEntity<AccountInfoDTO> getAccount(@PathVariable String storeId) {
        AccountInfo info = accountService.get(storeId);
        return new ResponseEntity<>(
                new AccountInfoDTO(info.getStoreId(), info.getMerchantId(), info.getMerchantPassword(), info.getBankTransactionEndpoint()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createAccount(@Valid @RequestBody AccountInfoDTO dto) {
        AccountInfo info = accountService.save(dto.getStoreId(), dto.getMerchantId(), dto.getMerchantPassword(), dto.getBankName());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity updateAccount(@Valid @RequestBody AccountInfoDTO dto) {
        AccountInfo info = accountService.update(dto.getStoreId(), dto.getMerchantId(), dto.getMerchantPassword(), dto.getBankName());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{storeId}", produces = "application/json")
    public ResponseEntity deleteAccount(@PathVariable String storeId) {
        accountService.remove(storeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
