package de.unistuttgart.iste.ese.api.ToDo;

import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing ToDo entities.
 * Extends the CrudRepository interface to provide basic CRUD operations.
 * Spring Data JPA will automatically generate the implementation at runtime.
 */
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    /**
     * Finds a ToDo entity by its ID.
     *
     * @param id The unique identifier of the ToDo.
     * @return The ToDo entity with the specified ID, or null if not found.
     */
    ToDo findById(long id);

}
