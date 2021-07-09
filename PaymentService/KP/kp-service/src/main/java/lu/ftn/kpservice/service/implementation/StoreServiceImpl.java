package lu.ftn.kpservice.service.implementation;

import lu.ftn.kpservice.exceptions.EntityNotFoundException;
import lu.ftn.kpservice.exceptions.InvalidOptionException;
import lu.ftn.kpservice.model.entity.Store;
import lu.ftn.kpservice.model.entity.User;
import lu.ftn.kpservice.model.enums.PaymentType;
import lu.ftn.kpservice.repository.StoreRepository;
import lu.ftn.kpservice.service.StoreService;
import lu.ftn.kpservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserService userService;

    @Override
    public Store addPaymentOption(String storeId, PaymentType type) {
        Store store = getById(storeId);

        if (store == null) {
            throw new EntityNotFoundException("Store");
        }

        store.getChosenPaymentTypes().add(type);
        return save(store);
    }

    @Override
    public Store setPreferredPaymentOption(String storeId, PaymentType type) {
        Store store = getById(storeId);

        if (store == null)
            throw new EntityNotFoundException("Store");

        if (!store.getChosenPaymentTypes().contains(type))
            throw new InvalidOptionException("Can't choose a non implemented payment type as preferred one");

        store.setPreferredPaymentType(type);
        return save(store);
    }

    @Override
    public Store removePaymentOption(String storeId, PaymentType type) {
        Store store = getById(storeId);

        if (store == null) {
            throw new EntityNotFoundException("Store");
        }

        store.getChosenPaymentTypes().remove(type);
        return save(store);
    }

    @Override
    public Store register(String name, String userId, String parentStoreId, String successWebhook, String failureWebhook, String errorWebhook) {
        Store store = new Store();
        store.setName(name);
        store.setTransactionSuccessWebhook(successWebhook);
        store.setTransactionFailureWebhook(failureWebhook);
        store.setTransactionErrorWebhook(errorWebhook);

        User user = userService.getUserById(userId);
        store.setUser(user);

        Store parentStore = getById(parentStoreId);
        if (parentStore != null) {
            store.setParentStore(parentStore);
            store = save(store);

            parentStore.getSubStores().add(store);
            user.setManagedStore(store);
            userService.saveUser(user);

            return save(parentStore);
        } else {
            store.setParentStore(null);
            store = save(store);
            user.setManagedStore(store);
            userService.saveUser(user);

            return store;
        }
    }

    @Override
    public Store getById(String id) {
        if (id == null)
            return null;
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public Store save(Store store) {
        return storeRepository.save(store);
    }
}
