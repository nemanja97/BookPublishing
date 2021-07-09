package lu.ftn.kpservice.repository;

import lu.ftn.kpservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
