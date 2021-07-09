package lu.ftn.repository;

import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipProcessRepository  extends JpaRepository<MembershipProcess, String> {

    MembershipProcess findByWriter(Writer writer);
  
}
