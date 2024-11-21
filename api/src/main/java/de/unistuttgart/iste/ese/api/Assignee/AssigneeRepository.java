package de.unistuttgart.iste.ese.api.Assignee;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssigneeRepository extends CrudRepository<Assignee, Long> {
    Assignee findByName(String name);
    Assignee findById(long id);

}
