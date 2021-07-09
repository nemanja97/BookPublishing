package lu.ftn.bank2service.repository;

import lu.ftn.bank2service.model.entity.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String> {
}
