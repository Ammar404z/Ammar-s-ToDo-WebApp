package de.unistuttgart.iste.ese.api.ToDo;

import java.util.List;

import de.unistuttgart.iste.ese.api.Assignee.Assignee;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * Entity class representing a ToDo item.
 * Maps to the "todos" table in the database.
 */
@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Size(min = 1, message = "Title must have at least 1 character")
    private String title;

    private String description;

    private boolean finished = false;

    @ManyToMany
    private List<Assignee> assigneeList;

    private long createdDate;

    @PositiveOrZero(message = "DueDate must be a valid Unix timestamp in milliseconds")
    private Long dueDate;

    private long finishedDate;

    /**
     * Category of the ToDo (e.g., "work", "personal").
     */
    private String category;

    // empty default constructor for JPA
    public ToDo() {
    }

    /**
     * Parameterized constructor to create a new ToDo instance.
     *
     * @param title        Title of the ToDo.
     * @param description  Description of the ToDo.
     * @param finished     Indicates whether the ToDo is finished or not.
     * @param assigneeList List of assignees associated with the ToDo.
     * @param createdDate  Creation date of the ToDo.
     * @param dueDate      Due date of the ToDo.
     * @param finishedDate Finished date of the ToDo.
     */
    public ToDo(String title, String description, boolean finished, List<Assignee> assigneeList, long createdDate,
            long dueDate, long finishedDate) {

        this.title = title;
        this.description = description;
        this.finished = finished;
        this.assigneeList = assigneeList;
        this.createdDate = System.currentTimeMillis();
        this.dueDate = dueDate;
        this.finishedDate = finishedDate;
    }

    // Getter and setter methods

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<Assignee> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(List<Assignee> assigneeList) {
        this.assigneeList = assigneeList;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public long getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(long finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
