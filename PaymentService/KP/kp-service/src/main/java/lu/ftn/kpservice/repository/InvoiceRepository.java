package lu.ftn.kpservice.repository;

import lu.ftn.kpservice.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
