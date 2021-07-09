package lu.ftn.bank1service.repository;

import lu.ftn.bank1service.model.entity.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, String> {
}
