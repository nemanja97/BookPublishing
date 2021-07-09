package lu.ftn.kpservice.helper;

import lu.ftn.kpservice.model.dto.*;
import lu.ftn.kpservice.model.entity.*;
import lu.ftn.kpservice.service.implementation.StoreServiceImpl;
import lu.ftn.kpservice.service.implementation.UserServiceImpl;

import java.util.stream.Collectors;

public class Converter {

    public static User userRegistrationDTOtoUser(UserRegistrationDTO dto) {
        // Only used when a store admin registers himself and his store
        // Not used for registering store merchants
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setActive(true);
        user.setRequiresPasswordChange(false);
        user.setRole(Role.ROLE_STORE_ADMIN);
        return user;
    }

    public static InvoicePaymentDTO invoiceToInvoicePaymentDTO(Invoice invoice,
                                                               String successRedirectUrl, String failureRedirectUrl) {
        return new InvoicePaymentDTO(
                invoice.getId(), invoice.getSeller().getId(),
                successRedirectUrl, failureRedirectUrl, null,
                invoice.getAmount(), invoice.getCurrency(),
                invoice.getItems().stream().map(InvoiceItem::getName).collect(Collectors.joining(", ")),
                invoice.getItems().stream().map(InvoiceItem::getDescription).collect(Collectors.joining(", "))
        );
    }

    public static InvoicePaymentDTO invoiceToInvoicePaymentDTO(Invoice invoice,
                                                               String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) {
        return new InvoicePaymentDTO(
                invoice.getId(), invoice.getSeller().getId(),
                successRedirectUrl, failureRedirectUrl, errorRedirectUrl,
                invoice.getAmount(), invoice.getCurrency(),
                invoice.getItems().stream().map(InvoiceItem::getName).collect(Collectors.joining(", ")),
                invoice.getItems().stream().map(InvoiceItem::getDescription).collect(Collectors.joining(", "))
        );
    }

    public static BankInvoiceCreationDTO invoiceToBankInvoiceCreationDTO(Invoice invoice,
                                                                    String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) {
        return new BankInvoiceCreationDTO(
                invoice.getId(), invoice.getSeller().getId(),
                successRedirectUrl, failureRedirectUrl, errorRedirectUrl,
                invoice.getAmount(), invoice.getCurrency()
        );
    }

    public static PayPalInvoiceCreationDTO invoiceToPayPalInvoiceCreationDTO(Invoice invoice,
                                                                             String successRedirectUrl, String failureRedirectUrl, String errorRedirectUrl) {
        return new PayPalInvoiceCreationDTO(
                invoice.getId(), invoice.getSeller().getId(),
                successRedirectUrl, failureRedirectUrl, errorRedirectUrl,
                invoice.getAmount(), invoice.getCurrency()
        );
    }

}
