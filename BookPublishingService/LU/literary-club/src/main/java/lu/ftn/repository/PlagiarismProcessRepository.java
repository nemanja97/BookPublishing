package lu.ftn.repository;

import lu.ftn.model.entity.PlagiarismProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlagiarismProcessRepository extends JpaRepository<PlagiarismProcess, String> {
}
