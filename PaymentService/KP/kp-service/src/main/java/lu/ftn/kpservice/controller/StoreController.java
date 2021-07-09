package lu.ftn.kpservice.controller;

import lu.ftn.kpservice.model.dto.StoreRegistrationDTO;
import lu.ftn.kpservice.model.entity.Store;
import lu.ftn.kpservice.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/store")
public class StoreController {

    @Autowired
    StoreService storeService;

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    //@PreAuthorize("hasAnyRole('ROLE_STORE_ADMIN', 'ROLE_STORE_MERCHANT')")
    public ResponseEntity registerStore(@Valid @RequestBody StoreRegistrationDTO dto) {
        Store store = storeService.register(dto.getName(), this.getCurrentUserId(), dto.getParentStoreId(),
                dto.getStoreTransactionSuccessWebhook(), dto.getStoreTransactionFailureWebhook(), dto.getStoreTransactionErrorWebhook());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{storeId}", produces = "application/json")
    public ResponseEntity getStoreDashboard() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
