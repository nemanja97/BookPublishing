package rs.ac.uns.ftn.uddproject.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.uddproject.model.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByEmail(String email);
}
