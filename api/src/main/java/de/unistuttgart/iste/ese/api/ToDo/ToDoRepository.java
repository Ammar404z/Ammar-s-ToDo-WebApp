package de.unistuttgart.iste.ese.api.ToDo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<ToDo, Long> {
ToDo findById(long id);

}
