package de.unistuttgart.iste.ese.api.ToDo;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.springframework.web.server.ResponseStatusException;

import de.unistuttgart.iste.ese.api.ApiVersion1;
import de.unistuttgart.iste.ese.api.Assignee.Assignee;
import de.unistuttgart.iste.ese.api.Assignee.AssigneeRepository;
import jakarta.validation.Valid;

@RestController
@ApiVersion1
public class ToDoController {
    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private AssigneeRepository assigneeRepository; //we use this in the PUT operation
    
    @GetMapping("/todos")
    public List<ToDo> getTodos(){
        List<ToDo> allTodos = (List<ToDo>) toDoRepository.findAll();
        return allTodos;
    }

    // get a single todo
    @GetMapping("/todos/{id}")
    public ToDo getToDo(@PathVariable ("id") long id){
        ToDo searchedToDo = toDoRepository.findById(id);
        if(searchedToDo != null){
            return searchedToDo;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ToDo with ID %s not found", id));
    }

    
    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createToDo(@Valid @RequestBody ToDoRequest toDoReq ){


        List<Long> assigneeIds = toDoReq.getAssigneeIdList();
        List<Assignee> assignees = Collections.emptyList(); // we set this to empty, because we need to handle the case that the requestbody doesnt include any assigneeIdlist.

        //make sure that the passed ids in the request body are not null nor empty, since we will be mapping the ids to the specific assignee
        if (assigneeIds != null && !assigneeIds.isEmpty()) {
            
            //check if the ids are unique
            Set<Long> uniqueIds = new HashSet<>(assigneeIds); //add all assignee ids to the set to make sure that they are unique
            if (uniqueIds.size() != assigneeIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IDs should be unique");
            }

            //check if all Assignee-IDs exist
            assignees = (List<Assignee>) assigneeRepository.findAllById(assigneeIds); // in this step we are mapping the assignee Ids that have been passed in in the request body to the specific assignees, we will use this list to create our Todo in the following
            if (assignees.size() != assigneeIds.size()) { //compare the size of the assignees found by the passed Ids and the size of the ID list, if they differ --> there are invalid Assignees
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"One or multiple Assignees are invalid");
            }

        }
        // find out the category of the todo using the todo model
        String modelPath = "/resources/model.pmml";
        TodoModel todoModel = new TodoModel(modelPath); //load the model
        String predictedCategory = todoModel.predictClass(toDoReq.getTitle());
        todoModel.unloadModel();

        //now we create our ToDo Object with all our needed Attributes (mapping step)
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

    @PutMapping("/todos/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ToDo updateToDo(@PathVariable("id") long id, @Valid @RequestBody ToDoRequest toDoReq){

        //search for the ToDo in the database
        ToDo existingToDo = toDoRepository.findById(id);
        if(existingToDo == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ToDo with ID %s not found", id));
        }

        List<Long> assigneeIds = toDoReq.getAssigneeIdList();
        List<Assignee> assignees = Collections.emptyList(); // we set this to empty, because we need to handle the case that the requestbody doesnt include any assigneeIdlist.

        if (assigneeIds != null &&!assigneeIds.isEmpty()) {
            //check if the ids are unique
            Set<Long> uniqueIds = new HashSet<>(assigneeIds); //add all assignee ids to the set to make sure that they are unique
            if (uniqueIds.size()!= assigneeIds.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The IDs should be unique");
            }

            //check if all Assignee-IDs exist
            assignees = (List<Assignee>) assigneeRepository.findAllById(assigneeIds); // in this step we are mapping the assignee Ids that have been passed in in the request body to the specific assignees, we will use this list to update our Todo in the following
            if (assignees.size() != assigneeIds.size()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"One or multiple Assignees are invalid");
                
            }

            
        }
        // find out the category of the todo using the todo model
        String modelPath = "/resources/model.pmml";
        TodoModel todoModel = new TodoModel(modelPath); //load the model
        String predictedCategory = todoModel.predictClass(toDoReq.getTitle());
        todoModel.unloadModel();

        existingToDo.setTitle(toDoReq.getTitle());
        existingToDo.setDescription(toDoReq.getDescription());
        existingToDo.setFinished(toDoReq.isFinished());
        existingToDo.setAssigneeList(assignees);
        existingToDo.setDueDate(toDoReq.getDueDate() != null ? toDoReq.getDueDate() : existingToDo.getDueDate()); // if the dueDate is null, keep the existing date, otherwise update it.
        existingToDo.setFinishedDate(toDoReq.isFinished() ? System.currentTimeMillis() : 0); // if the todo is finished, set the finishedDate, otherwise keep it 0. 
        existingToDo.setCategory(predictedCategory);

        return toDoRepository.save(existingToDo);


}


    @DeleteMapping("/todos/{id}")
    public void deleteToDo(@PathVariable("id") long id){
       ToDo existingToDo = toDoRepository.findById(id);

       if(existingToDo == null){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("ToDo with ID %s not found", id));
        }

        toDoRepository.delete(existingToDo);

    }
}

    