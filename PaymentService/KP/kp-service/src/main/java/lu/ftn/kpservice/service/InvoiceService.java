package lu.ftn.kpservice.service;

import lu.ftn.kpservice.model.entity.Invoice;

public interface InvoiceService {

    Invoice save(Invoice invoice);

    Invoice getById(String id);
}
