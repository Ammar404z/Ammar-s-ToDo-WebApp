package de.unistuttgart.iste.ese.api.ToDo;

import java.util.List;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) for transferring ToDo-related data between client
 * and server.
 * Contains validation annotations to ensure proper data integrity.
 */
public class ToDoRequest {

    @Size(min = 1, message = "Title must be at least 1 character long.")
    private String title;

    private String description;

    private boolean finished;

    private List<Long> assigneeIdList; // Assignee IDs

    @PositiveOrZero(message = "DueDate has to be a valid Unix timestamp in ms")
    private Long dueDate; // Unix-Timestamp in Milliseconds

    /**
     * Default constructor for the ToDoRequest class.
     */
    public ToDoRequest() {
    }

    /**
     * Parameterized constructor for creating a ToDoRequest object.
     *
     * @param title          The title of the ToDo.
     * @param description    The description of the ToDo.
     * @param assigneeIdList The list of assignee IDs associated with the ToDo.
     * @param dueDate        The due date of the ToDo in Unix timestamp format.
     */
    public ToDoRequest(String title, String description, List<Long> assigneeIdList, Long dueDate) {
        this.title = title;
        this.description = description;
        this.assigneeIdList = assigneeIdList;

        this.dueDate = dueDate;
    }

    // Getters und setters

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