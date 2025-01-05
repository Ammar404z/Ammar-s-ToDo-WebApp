package de.unistuttgart.iste.ese.api.Assignee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.unistuttgart.iste.ese.api.ApiVersion1;
import jakarta.validation.Valid;

/**
 * Controller for managing API endpoints related to Assignees.
 * Handles incoming HTTP requests and delegates business logic to the
 * AssigneeService.
 */
@RestController
@ApiVersion1
public class AssigneeController {

    @Autowired
    private AssigneeService assigneeService;

    /**
     * Retrieves all assignees from the database.
     *
     * @return A list of all assignees.
     */
    @GetMapping("/assignees")
    public List<Assignee> getAssignees() {
        return assigneeService.getAllAssignees();
    }

    /**
     * Retrieves a specific assignee based on the provided ID.
     *
     * @param id The ID of the assignee to retrieve.
     * @return The assignee with the specified ID.
     */
    @GetMapping("/assignees/{id}")
    public Assignee getAssignee(@PathVariable("id") long id) {
        return assigneeService.getAssigneeById(id);
    }

    /**
     * Creates a new assignee and saves it to the database.
     *
     * @param requestBody The request body containing the details of the assignee to
     *                    create.
     * @return The newly created assignee.
     */
    @PostMapping("/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignee createAssignee(@Valid @RequestBody Assignee requestBody) {
        return assigneeService.createAssignee(requestBody);
    }

    /**
     * Updates an existing assignee with the provided details.
     *
     * @param id          The ID of the assignee to update.
     * @param requestBody The request body containing updated details for the
     *                    assignee.
     * @return The updated assignee.
     */
    @PutMapping("/assignees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Assignee updateAssignee(@PathVariable("id") long id, @Valid @RequestBody Assignee requestBody) {
        return assigneeService.updateAssignee(id, requestBody);
    }

    /**
     * Deletes an assignee by ID and removes their association with any related
     * ToDos.
     *
     * @param id The ID of the assignee to delete.
     */
    @DeleteMapping("/assignees/{id}")
    public void deleteAssignee(@PathVariable("id") long id) {
        assigneeService.deleteAssignee(id);
    }
}