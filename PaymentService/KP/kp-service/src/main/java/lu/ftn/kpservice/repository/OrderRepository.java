package lu.ftn.kpservice.repository;

import lu.ftn.kpservice.model.CustomOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<CustomOrder, Long> {

    public Optional<CustomOrder> findByOrderUUID(String orderUUID);

}
