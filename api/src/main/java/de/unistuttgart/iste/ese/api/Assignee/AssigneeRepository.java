package de.unistuttgart.iste.ese.api.Assignee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Assignee entities.
 * Extends CrudRepository to provide basic CRUD operations.
 */
@Repository
public interface AssigneeRepository extends CrudRepository<Assignee, Long> {

    /**
     * Finds an assignee by their name.
     *
     * @param name The name of the assignee to search for.
     * @return The Assignee object with the specified name, or null if not found.
     */
    Assignee findByName(String name);

    /**
     * Finds an assignee by their ID.
     *
     * @param id The ID of the assignee to search for.
     * @return The Assignee object with the specified ID, or null if not found.
     */
    Assignee findById(long id);
}