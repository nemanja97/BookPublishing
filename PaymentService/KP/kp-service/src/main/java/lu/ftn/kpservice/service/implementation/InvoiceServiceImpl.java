package lu.ftn.kpservice.service.implementation;

import lu.ftn.kpservice.model.entity.Invoice;
import lu.ftn.kpservice.repository.InvoiceRepository;
import lu.ftn.kpservice.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {


    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getById(String id) {
        return invoiceRepository.findById(id).orElse(null);
    }
}
