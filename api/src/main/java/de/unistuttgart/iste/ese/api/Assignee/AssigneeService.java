package de.unistuttgart.iste.ese.api.Assignee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import de.unistuttgart.iste.ese.api.ToDo.ToDo;
import de.unistuttgart.iste.ese.api.ToDo.ToDoRepository;

/**
 * Service class for managing Assignee-related business logic.
 */
@Service
public class AssigneeService {

    @Autowired
    AssigneeRepository assigneeRepository;
    @Autowired
    ToDoRepository toDoRepository;

    /**
     * Retrieves all assignees from the database.
     *
     * @return A list of all assignees.
     */
    public List<Assignee> getAllAssignees() {
        List<Assignee> allAssignees = (List<Assignee>) assigneeRepository.findAll();
        return allAssignees;
    }

    /**
     * Retrieves a specific assignee by their ID.
     *
     * @param id The ID of the assignee to retrieve.
     * @return The assignee with the specified ID.
     * @throws ResponseStatusException If the assignee is not found.
     */
    public Assignee getAssigneeById(long id) {
        Assignee searchedAssignee = assigneeRepository.findById(id);

        if (searchedAssignee != null) {
            return searchedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Assignee with ID %s not found !", id));
    }

    /**
     * Creates a new assignee and saves it to the database.
     *
     * @param requestBody The details of the assignee to create.
     * @return The newly created assignee.
     */
    public Assignee createAssignee(Assignee requestbody) {
        Assignee assignee = new Assignee(requestbody.getPrename(), requestbody.getName(), requestbody.getEmail());
        Assignee savedAssignee = assigneeRepository.save(assignee);
        return savedAssignee;
    }

    /**
     * Updates an existing assignee with new details.
     *
     * @param id          The ID of the assignee to update.
     * @param requestBody The updated details for the assignee.
     * @return The updated assignee.
     * @throws ResponseStatusException If the assignee is not found.
     */
    public Assignee updateAssignee(long id, Assignee requestbody) {
        requestbody.setId(id);
        Assignee assigneeToUpdate = assigneeRepository.findById(id);
        if (assigneeToUpdate != null) {
            Assignee savedAssignee = assigneeRepository.save(requestbody);
            return savedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Assignee with ID %s not found!", id));
    }

    /**
     * Deletes an assignee by their ID and removes their association with any ToDos.
     *
     * @param id The ID of the assignee to delete.
     * @throws ResponseStatusException If the assignee is not found.
     */
    public void deleteAssignee(long id) {
        // check if the assignee exists
        Assignee assigneeToDelete = assigneeRepository.findById(id);
        if (assigneeToDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("Assignee with ID %s not found!", id));
        }

        // remove all assignees from all associated ToDos
        List<ToDo> allTodos = (List<ToDo>) toDoRepository.findAll();
        for (ToDo todo : allTodos) {
            List<Assignee> assigneeList = todo.getAssigneeList();
            if (assigneeList != null && assigneeList.contains(assigneeToDelete)) {
                assigneeList.remove(assigneeToDelete);
                toDoRepository.save(todo); // Update the ToDo
            }
        }

        // Delete the assignee
        assigneeRepository.deleteById(id);
    }

}
