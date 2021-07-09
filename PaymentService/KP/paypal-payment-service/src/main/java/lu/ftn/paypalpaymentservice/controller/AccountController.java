package lu.ftn.paypalpaymentservice.controller;

import lu.ftn.paypalpaymentservice.model.AccountInfo;
import lu.ftn.paypalpaymentservice.model.dto.AccountInfoDTO;
import lu.ftn.paypalpaymentservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "api/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping(path = "/{storeId}", produces = "application/json")
    public ResponseEntity<AccountInfoDTO> getAccount(@PathVariable String storeId) {
        AccountInfo info = accountService.get(storeId);
        return new ResponseEntity<>(
                new AccountInfoDTO(info.getStoreId(), info.getClientId(), info.getClientSecret()), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity createAccount(@Valid @RequestBody AccountInfoDTO dto) {
        AccountInfo info = accountService.save(dto.getStoreId(), dto.getClientId(), dto.getClientSecret());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity updateAccount(@Valid @RequestBody AccountInfoDTO dto) {
        AccountInfo info = accountService.update(dto.getStoreId(), dto.getClientId(), dto.getClientSecret());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{storeId}", produces = "application/json")
    public ResponseEntity deleteAccount(@PathVariable String storeId) {
        accountService.remove(storeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
