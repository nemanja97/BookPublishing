package lu.ftn.repository;

import lu.ftn.model.entity.EditorBookTask;
import lu.ftn.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EditorBookTaskRepository extends JpaRepository<EditorBookTask, String> {

    List<EditorBookTask> findAllByEditor(User editor);
}
