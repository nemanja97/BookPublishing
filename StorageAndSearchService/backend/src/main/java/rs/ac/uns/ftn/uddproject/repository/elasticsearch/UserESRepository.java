package rs.ac.uns.ftn.uddproject.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.uddproject.model.entity.elasticsearch.UserES;

@Repository
public interface UserESRepository extends ElasticsearchRepository<UserES, String> {}
