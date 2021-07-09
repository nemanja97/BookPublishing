package lu.ftn.repository;

import lu.ftn.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, String> {
    Reader findByUsername(String username);
    List<Reader> findByBetaReader(boolean betareader);
}
