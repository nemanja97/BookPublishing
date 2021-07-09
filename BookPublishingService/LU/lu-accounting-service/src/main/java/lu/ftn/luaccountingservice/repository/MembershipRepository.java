package lu.ftn.luaccountingservice.repository;

import lu.ftn.luaccountingservice.model.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership,String> {
}
