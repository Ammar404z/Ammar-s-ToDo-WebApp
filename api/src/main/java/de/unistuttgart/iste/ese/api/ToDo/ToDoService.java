package de.unistuttgart.iste.ese.api.ToDo;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import de.unistuttgart.iste.ese.api.Assignee.Assignee;
import de.unistuttgart.iste.ese.api.Assignee.AssigneeRepository;

/**
 * Service class for managing ToDos.
 * Contains the business logic for CRUD operations.
 */
@Service
public class ToDoService {

    @Autowired
    ToDoRepository toDoRepository;
    @Autowired
    AssigneeRepository assigneeRepository;

    /**
     * Retrieves all ToDos from the repository.
     *
     * @return List of all ToDos.
     */
    public List<ToDo> getAllToDos() {
        List<ToDo> allTodos = (List<ToDo>) toDoRepository.findAll();
        return allTodos;
    }

    /**
     * Retrieves a single ToDo by its ID.
     *
     * @param id The ID of the ToDo to retrieve.
     * @return The ToDo object.
     * @throws ResponseStatusException if the ToDo is not found.
     */
    public ToDo getToDoById(long id) {
        ToDo searchedToDo = toDoRepository.findById(id);
        if (searchedToDo != null) {
            return searchedToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ToDo with ID %s not found", id));
    }

    /**
     * Creates a new ToDo based on the provided request.
     *
     * @param toDoReq The request object containing ToDo details.
     * @return The created ToDo.
     */
    public ToDo createToDo(ToDoRequest toDoReq) {
        List<Long> assigneeIds = toDoReq.getAssigneeIdList();

        // We set this to empty, because we need to handle the case that the requestbody
        // doesnt include any assigneeIdlist.
        List<Assignee> assignees = Collections.emptyList();

        // make sure that the passed ids in the request body are not null nor empty,
        // since we will be mapping the ids to the specific assignee
        if (assigneeIds != null && !assigneeIds.isEmpty()) {

            // check if the ids are unique, by adding all assigneeIds to the set
            Set<Long> uniqueIds = new HashSet<>(assigneeIds);
            if (uniqueIds.size() != assigneeIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IDs should be unique");
            }

            // check if all Assignee-IDs exist.
            // In this step we are mapping the assignee Ids that have been passed in in the
            // request body to the specific assignees, we will use this list to create our
            // Todo in the following
            assignees = (List<Assignee>) assigneeRepository.findAllById(assigneeIds);

            // compare the size of the assignees found by the passed Ids and the size of the
            // ID list, if they differ --> there are invalid Assignees
            if (assignees.size() != assigneeIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or multiple Assignees are invalid");
            }

        }
        // find out the category of the todo using the todo model
        String modelPath = "/resources/model.pmml";
        TodoModel todoModel = new TodoModel(modelPath); // load the model
        String predictedCategory = todoModel.predictClass(toDoReq.getTitle());
        todoModel.unloadModel();

        // now we create our ToDo Object with all our needed Attributes (mapping step)
        ToDo toDo = new ToDo();
        toDo.setTitle(toDoReq.getTitle());
        toDo.setDescription(toDoReq.getDescription());
        toDo.setFinished(false);
        toDo.setAssigneeList(assignees); // Set the mapped assignees
        toDo.setCreatedDate(System.currentTimeMillis());
        toDo.setDueDate(toDoReq.getDueDate());
        toDo.setCategory(predictedCategory);

        return toDoRepository.save(toDo);
    }

    /**
     * Updates an existing ToDo with the provided details.
     *
     * @param id      The ID of the ToDo to update.
     * @param toDoReq The request object containing updated ToDo details.
     * @return The updated ToDo.
     * @throws ResponseStatusException if the ToDo is not found or assignees are
     *                                 invalid.
     */
    public ToDo updateToDo(long id, ToDoRequest toDoReq) {

        // search for the ToDo in the database
        ToDo existingToDo = toDoRepository.findById(id);
        if (existingToDo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ToDo with ID %s not found", id));
        }

        List<Long> assigneeIds = toDoReq.getAssigneeIdList();

        // we set this to empty, because we need to handle the case that the requestbody
        // doesnt include any assigneeIdlist.
        List<Assignee> assignees = Collections.emptyList();

        if (assigneeIds != null && !assigneeIds.isEmpty()) {
            // check if the ids are unique, by adding all assigneeIds to the set
            Set<Long> uniqueIds = new HashSet<>(assigneeIds);
            if (uniqueIds.size() != assigneeIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IDs should be unique");
            }

            // check if all Assignee-IDs exist.
            // In this step we are mapping the assignee Ids that have been passed in in the
            // request body to the specific assignees, we will use this list to create our
            // Todo in the following
            assignees = (List<Assignee>) assigneeRepository.findAllById(assigneeIds);
            if (assignees.size() != assigneeIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or multiple Assignees are invalid");

            }

        }
        // find out the category of the todo using the todo model
        String modelPath = "/resources/model.pmml";
        TodoModel todoModel = new TodoModel(modelPath); // load the model
        String predictedCategory = todoModel.predictClass(toDoReq.getTitle());
        todoModel.unloadModel();

        existingToDo.setTitle(toDoReq.getTitle());
        existingToDo.setDescription(toDoReq.getDescription());
        existingToDo.setFinished(toDoReq.isFinished());
        existingToDo.setAssigneeList(assignees);

        // if the dueDate is null, keep the existing date, otherwise update it
        existingToDo.setDueDate(toDoReq.getDueDate() != null ? toDoReq.getDueDate() : existingToDo.getDueDate());

        // if the todo is finished set the finishedDate, otherwise keep it 0.
        existingToDo.setFinishedDate(toDoReq.isFinished() ? System.currentTimeMillis() : 0);
        existingToDo.setCategory(predictedCategory);

        return toDoRepository.save(existingToDo);

    }

    /**
     * Deletes a ToDo by its ID.
     *
     * @param id The ID of the ToDo to delete.
     * @throws ResponseStatusException if the ToDo is not found.
     */
    public void deleteToDo(long id) {
        ToDo existingToDo = toDoRepository.findById(id);

        if (existingToDo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ToDo with ID %s not found", id));
        }

        toDoRepository.delete(existingToDo);
    }
}
