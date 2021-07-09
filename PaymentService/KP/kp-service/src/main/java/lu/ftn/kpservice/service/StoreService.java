package lu.ftn.kpservice.service;

import lu.ftn.kpservice.model.entity.Store;
import lu.ftn.kpservice.model.enums.PaymentType;

public interface StoreService {

    Store addPaymentOption(String storeId, PaymentType type);

    Store setPreferredPaymentOption(String storeId, PaymentType type);

    Store removePaymentOption(String storeId, PaymentType type);

    Store register(String name, String userId, String parentStoreId, String successWebhook, String failureWebhook, String errorWebhook);

    Store getById(String id);

    Store save(Store store);
}
