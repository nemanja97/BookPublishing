package lu.ftn.luaccountingservice.repository;

import lu.ftn.luaccountingservice.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}
