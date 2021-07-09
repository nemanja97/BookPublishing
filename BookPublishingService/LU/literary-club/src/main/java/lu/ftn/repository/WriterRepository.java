package lu.ftn.repository;

import lu.ftn.model.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, String> {
}
