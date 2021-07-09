package lu.ftn.repository;

import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByRole(Role role);

    List<User> findAllByRole(Role role);
}
