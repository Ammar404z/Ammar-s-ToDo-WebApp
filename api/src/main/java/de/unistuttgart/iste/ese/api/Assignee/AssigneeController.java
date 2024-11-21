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
import org.springframework.web.server.ResponseStatusException;

import de.unistuttgart.iste.ese.api.ApiVersion1;
import de.unistuttgart.iste.ese.api.ToDo.ToDo;
import de.unistuttgart.iste.ese.api.ToDo.ToDoRepository;
import jakarta.validation.Valid;

@RestController
@ApiVersion1
public class AssigneeController {

    @Autowired
    private AssigneeRepository assigneeRepository;

    @Autowired
    private ToDoRepository toDoRepository;

    //ToDo: Get All Assignees
    @GetMapping("/assignees")
    public List<Assignee> getAssignees() {
        List<Assignee> allAssignees = (List<Assignee>) assigneeRepository.findAll();
        return allAssignees;
    }


    // Get a Single Assignee
    @GetMapping("/assignees/{id}")
    public Assignee getAssignee(@PathVariable("id") long id) {
        Assignee searchedAssignee = assigneeRepository.findById(id);

        if (searchedAssignee != null) {
            return searchedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Assignee with ID %s not found !", id));
    }

    // Create a Single Assignee
    @PostMapping("/assignees")
    @ResponseStatus(HttpStatus.CREATED)
    public Assignee createAssignee(@Valid @RequestBody Assignee requestbody) {
        Assignee assignee = new Assignee(requestbody.getPrename(), requestbody.getName(), requestbody.getEmail());
        Assignee savedAssignee = assigneeRepository.save(assignee);
        return savedAssignee;
    }

    // Update a Single Assignee
    @PutMapping("/assignees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Assignee updateAssignee(@PathVariable("id") long id, @Valid @RequestBody Assignee requestbody) {
        requestbody.setId(id);
        Assignee assigneeToUpdate = assigneeRepository.findById(id);
        if (assigneeToUpdate != null) {
            Assignee savedAssignee = assigneeRepository.save(requestbody);
            return savedAssignee;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Assignee with ID %s not found!", id));
    }

    @DeleteMapping("/assignees/{id}")
    public void deleteAssignee(@PathVariable("id") long id) {
        // Assignee prüfen
        Assignee assigneeToDelete = assigneeRepository.findById(id);
        if (assigneeToDelete == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Assignee with ID %s not found!", id));
        }
    
        // Alle ToDos durchgehen und den Assignee entfernen
        List<ToDo> allTodos = (List<ToDo>) toDoRepository.findAll();
        for (ToDo todo : allTodos) {
            List<Assignee> assigneeList = todo.getAssigneeList();
            if (assigneeList != null && assigneeList.contains(assigneeToDelete)) {
                assigneeList.remove(assigneeToDelete);
                toDoRepository.save(todo); // ToDo aktualisieren
            }
        }
    
        // Assignee löschen
        assigneeRepository.deleteById(id);
    }
}

