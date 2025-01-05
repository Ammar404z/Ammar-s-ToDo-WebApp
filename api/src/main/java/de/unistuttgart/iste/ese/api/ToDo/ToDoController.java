package de.unistuttgart.iste.ese.api.ToDo;

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
 * REST controller for managing ToDo items.
 * Provides endpoints for CRUD operations on ToDos.
 */
@RestController
@ApiVersion1
public class ToDoController {
    @Autowired
    ToDoService toDoService;

    /**
     * Retrieves a list of all ToDos.
     * 
     * @return List of ToDos.
     */
    @GetMapping("/todos")
    public List<ToDo> getTodos() {
        return toDoService.getAllToDos();
    }

    /**
     * Retrieves a single ToDo by its ID.
     *
     * @param id The ID of the ToDo to retrieve.
     * @return The ToDo object.
     */
    @GetMapping("/todos/{id}")
    public ToDo getToDo(@PathVariable("id") long id) {
        return toDoService.getToDoById(id);
    }

    /**
     * Creates a new ToDo.
     *
     * @param toDoReq The request body containing ToDo details.
     * @return The created ToDo.
     */
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createToDo(@Valid @RequestBody ToDoRequest toDoReq) {
        return toDoService.createToDo(toDoReq);
    }

    /**
     * Updates an existing ToDo by its ID.
     *
     * @param id      The ID of the ToDo to update.
     * @param toDoReq The request body containing updated ToDo details.
     * @return The updated ToDo.
     */
    @PutMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody ToDoRequest toDoReq) {
        return toDoService.updateToDo(id, toDoReq);
    }

    /**
     * Deletes a ToDo by its ID.
     *
     * @param id The ID of the ToDo to delete.
     */
    @DeleteMapping("/todos/{id}")
    public void deleteToDo(@PathVariable("id") long id) {
        toDoService.deleteToDo(id);
    }
}
