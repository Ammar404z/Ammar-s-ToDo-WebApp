package de.unistuttgart.iste.ese.api.ToDo;

import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ToDoRequest{
    
   @Size(min = 1, message = "Title must be at least 1 character long.")
    private String title;

    private String description;

    private boolean finished;


    private List<Long> assigneeIdList; // Assignee IDs

    @PositiveOrZero(message = "DueDate has to be a valid Unix timestamp in ms")
    private Long dueDate; // Unix-Timestamp in Millisekunden


    public ToDoRequest(){}

    public ToDoRequest(String title, String description, List<Long> assigneeIdList, Long dueDate) {
        this.title = title;
         this.description = description;
         this.assigneeIdList = assigneeIdList; 

         this.dueDate = dueDate;
        }


    // Getter und Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Long> getAssigneeIdList() {
        return assigneeIdList;
    }

    public void setAssigneeIdList(List<Long> assigneeIdList) {
        this.assigneeIdList = assigneeIdList;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }
}